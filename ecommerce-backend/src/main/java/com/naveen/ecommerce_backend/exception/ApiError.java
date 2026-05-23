package com.naveen.ecommerce_backend.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApiError {

    private final LocalDateTime timeStamp;

    private int status;

    private String error;

    private final String message;

    private String path;
}
