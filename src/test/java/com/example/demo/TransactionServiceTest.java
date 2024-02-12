package com.example.demo;

import com.example.demo.api.PostTransactionRequest;
import com.example.demo.domain.BankAccount;
import com.example.demo.exception.InternalTransactionException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.TransactionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TransactionServiceTest {
    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    public void testCreateTransaction_EnoughBalance() {
        // Arrange
        Long accountId = 1L;
        BigDecimal initialBalance = BigDecimal.valueOf(500);
        BigDecimal transactionAmount = BigDecimal.valueOf(300);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(accountId);
        bankAccount.setBalance(initialBalance);

        PostTransactionRequest postTransactionRequest = new PostTransactionRequest();
        postTransactionRequest.setAmount(transactionAmount);

        BankAccount updatedBankAccount = new BankAccount();
        updatedBankAccount.setId(accountId);
        updatedBankAccount.setBalance(initialBalance.add(transactionAmount));

        when(bankAccountRepository.findById(accountId)).thenReturn(Optional.of(bankAccount));
        when(bankAccountRepository.saveAndFlush(any())).thenReturn(updatedBankAccount);

        // Act
        transactionService.createTransaction(accountId, postTransactionRequest);

        // Assert
        assertEquals(updatedBankAccount.getBalance(), bankAccount.getBalance());
        verify(bankAccountRepository, times(1)).saveAndFlush(bankAccount);
        verify(transactionRepository, times(1)).saveAndFlush(any());
    }

    @Test
    public void testCreateTransaction_NotEnoughBalance() {
        // Arrange
        Long accountId = 1L;
        BigDecimal initialBalance = BigDecimal.valueOf(10);
        BigDecimal transactionAmount = BigDecimal.valueOf(100);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(accountId);
        bankAccount.setBalance(initialBalance);

        PostTransactionRequest postTransactionRequest = new PostTransactionRequest();
        postTransactionRequest.setAmount(transactionAmount);

        when(bankAccountRepository.findById(accountId)).thenReturn(Optional.of(bankAccount));

        // Act and Assert
        assertThrows(InternalTransactionException.class, () ->
                transactionService.createTransaction(accountId, postTransactionRequest));

        verify(bankAccountRepository, times(1)).saveAndFlush(any());
        verify(transactionRepository, times(1)).saveAndFlush(any());
    }

    @Test
    public void testCreateTransaction_AccountNotFound() {
        // Arrange
        Long accountId = 1L;
        PostTransactionRequest postTransactionRequest = new PostTransactionRequest();
        postTransactionRequest.setAmount(BigDecimal.TEN);
        when(bankAccountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () ->
                transactionService.createTransaction(accountId, postTransactionRequest));

        verify(bankAccountRepository, never()).saveAndFlush(any());
        verify(transactionRepository, never()).saveAndFlush(any());
    }
}
