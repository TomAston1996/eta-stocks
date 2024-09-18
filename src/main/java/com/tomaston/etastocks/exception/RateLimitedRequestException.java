package com.tomaston.etastocks.exception;

public class RateLimitedRequestException extends RuntimeException {

    public RateLimitedRequestException(String message) {
        super(message);
    }

    public RateLimitedRequestException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
