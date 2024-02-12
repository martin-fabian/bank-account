package com.example.demo.api;

import lombok.Data;

@Data
public class SubjectResponse {
    private Long id;
    private String name;
    private String lastName;
    private int numberOfAccounts;
}
