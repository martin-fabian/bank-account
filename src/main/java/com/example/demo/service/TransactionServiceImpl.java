package com.example.demo.service;

import com.example.demo.api.PostTransactionRequest;
import com.example.demo.domain.BankAccount;
import com.example.demo.domain.Transaction;
import com.example.demo.exception.InternalTransactionException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final BigDecimal MIN_BALANCE = BigDecimal.valueOf(200);

    @Override
    public void createTransaction(Long accountId, PostTransactionRequest request) {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(accountId);
        optionalBankAccount.ifPresent(bankAccount -> {
            var newBalance = bankAccount.getBalance().add(request.getAmount());
            if (newBalance.compareTo(MIN_BALANCE) < 0) {
                saveTransactionAndBankAccount(bankAccount, newBalance, request);
                throw new InternalTransactionException("Actual balance is lower than: " + MIN_BALANCE);
            } else if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Balance would be negative, transaction was not saved");
            }
            saveTransactionAndBankAccount(bankAccount, newBalance, request);
        });
        optionalBankAccount.orElseThrow(() -> new NotFoundException("Bank account not found with id: " + accountId));
    }

    private void saveTransactionAndBankAccount(BankAccount bankAccount, BigDecimal newBalance, PostTransactionRequest request) {
        Transaction transaction = new Transaction();
        bankAccount.setBalance(newBalance);
        transaction.setAmount(request.getAmount());
        transaction.setBankAccount(bankAccount);
        transactionRepository.saveAndFlush(transaction);
        bankAccountRepository.saveAndFlush(bankAccount);
    }
}
