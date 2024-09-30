package com.tomaston.etastocks.exception;

public class NotFoundRequestException extends RuntimeException {
    public NotFoundRequestException(String message) {
        super(message);
    }

    public NotFoundRequestException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
