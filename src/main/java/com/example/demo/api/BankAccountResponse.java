package com.example.demo.api;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccountResponse {
    private Long id;

    private String prefix;
    private String suffix;

    private BigDecimal balance;
    private Long subject;
}
