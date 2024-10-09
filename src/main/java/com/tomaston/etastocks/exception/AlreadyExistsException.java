package com.tomaston.etastocks.exception;

public class AlreadyExistsException extends  RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
