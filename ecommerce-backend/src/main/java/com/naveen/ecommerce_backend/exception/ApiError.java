package com.naveen.ecommerce_backend.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiError {
    private final String message;
    private final LocalDateTime timeStamp;

    ApiError(String message)
    {
        this.message = message;
        timeStamp = LocalDateTime.now();
    }
}
