package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public class InternalTransactionException extends RuntimeException {
    public InternalTransactionException(String message) {
        super(message);
        System.out.println("Internal transaction failed: " + message);
    }
}
