package com.tomaston.etastocks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(BadRequestException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {RateLimitedRequestException.class})
    public ResponseEntity<Object> handleRateLimitedRequestException(RateLimitedRequestException e) {
        ApiException rateLimitException = new ApiException(
                e.getMessage(),
                HttpStatus.TOO_MANY_REQUESTS,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(rateLimitException, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(value = {NotFoundRequestException.class})
    public ResponseEntity<Object> handleNotFoundRequestException(NotFoundRequestException e) {
        ApiException notFoundException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(notFoundException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ServerErrorException.class})
    public ResponseEntity<Object> handleServerErrorException(ServerErrorException e) {
        ApiException serverErrorException = new ApiException(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(serverErrorException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
