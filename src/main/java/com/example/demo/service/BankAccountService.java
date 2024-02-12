package com.example.demo.service;

import com.example.demo.api.BankAccountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BankAccountService {

    Page<BankAccountResponse> findAll(Pageable pageable);

    void applyForLoan(Long subjectId);
}
