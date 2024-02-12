package com.example.demo.service;

import com.example.demo.api.BankAccountResponse;
import com.example.demo.domain.BankAccount;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.BankAccountMapper;
import com.example.demo.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<BankAccountResponse> findAll(Pageable pageable) {
        return bankAccountRepository.findAll(pageable).map(bankAccountMapper::map);
    }

    @Transactional
    public void applyForLoan(Long subjectId) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findBySubject_Id(subjectId);
        if (bankAccount.isPresent()) {
            if (bankAccount.get().getBalance().compareTo(BigDecimal.TEN) < 0) {
                throw new IllegalStateException("You cannot apply for loan with negative balance");
            }
            bankAccount.get().setApplyForLoan(true);
            bankAccountRepository.saveAndFlush(bankAccount.get());
        }
        bankAccount.orElseThrow(() -> new NotFoundException("Provided subjectId: " + subjectId + " was not found: "));
    }
}
