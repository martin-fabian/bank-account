package com.example.demo.repository;

import com.example.demo.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long>, JpaSpecificationExecutor<BankAccount> {

    @Query("SELECT COUNT(ba) FROM BankAccount ba WHERE ba.subject.id = :subject")
    int numberOfAccounts(Long subject);

    @Query("SELECT ba FROM BankAccount ba WHERE ba.subject.id = :subjectId")
    Optional<BankAccount> findBySubject_Id(Long subjectId);
}
