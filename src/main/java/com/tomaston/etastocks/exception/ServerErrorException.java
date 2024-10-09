package com.tomaston.etastocks.exception;

public class ServerErrorException extends RuntimeException {

    public ServerErrorException(String message) {
        super(message);
    }

    public ServerErrorException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
