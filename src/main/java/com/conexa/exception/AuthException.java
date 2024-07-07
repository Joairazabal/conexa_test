package com.conexa.exception;

import lombok.Data;

@Data
public class AuthException extends RuntimeException {

    private int httpCode;
    private String code;

    public AuthException(String code, int statusCode, String message) {
        super(message);
        this.httpCode = statusCode;
        this.code = code;
    }
}
