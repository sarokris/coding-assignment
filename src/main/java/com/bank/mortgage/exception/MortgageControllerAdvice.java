package com.bank.mortgage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class MortgageControllerAdvice {

    @ExceptionHandler(InterestRateNotFoundException.class)
    public ResponseEntity<Map<String, ?>> handleHolidayExceptioException(InterestRateNotFoundException ex) {
        Map<String, String> message = Map.of("message", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}