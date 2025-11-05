package com.bank.mortgage.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class MortgageControllerAdvice {

    @ExceptionHandler(InterestRateNotFoundException.class)
    public ResponseEntity<Map<String, ?>> handleInterestRateNotFoundException(InterestRateNotFoundException ex) {
        Map<String, String> message = Map.of("message", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MortgageProcessingException.class)
    public ResponseEntity<Map<String, ?>> handleMortgageProcessingException(MortgageProcessingException ex) {
        Map<String, String> message = Map.of("message", ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage,
                        (msg1, msg2) -> msg1
                ));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}