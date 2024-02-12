package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @SequenceGenerator(name = "bank_accounts_seq", sequenceName = "bank_accounts_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_accounts_seq")
    private Long id;

    private String prefix;
    private String suffix;

    private boolean applyForLoan;

    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject")
    private Subject subject;

    @OneToMany(mappedBy = "bankAccount")
    private List<Transaction> transactions;
}
