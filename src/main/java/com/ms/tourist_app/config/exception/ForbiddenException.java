package com.ms.tourist_app.config.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends RuntimeException{
    private HttpStatus status;

    public ForbiddenException(String message) {
        super(message);
        this.status = HttpStatus.FORBIDDEN;
    }
}
