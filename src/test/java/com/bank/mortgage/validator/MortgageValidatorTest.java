package com.bank.mortgage.validator;

import com.bank.mortgage.dto.MortgageCheckRequest;
import com.bank.mortgage.exception.MortgageProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MortgageValidatorTest {

    MortgageValidator validator;

    @BeforeEach
    void setup() {
        validator = new MortgageValidator();
    }


    @Test
    void shouldThrowExceptionWhenLoanExceedsFourTimesIncome() {
        var request = new MortgageCheckRequest(
                new BigDecimal("50000"),
                20,
                new BigDecimal("250001"), // exceeds 4 * 50000
                new BigDecimal("300000")
        );

        assertThatThrownBy(() -> validator.validate(request))
                .isInstanceOf(MortgageProcessingException.class)
                .hasMessage("Loan value cannot exceed 4 times the income");
    }

    @Test
    void shouldThrowExceptionWhenLoanExceedsHomeValue() {
        var request = new MortgageCheckRequest(
                new BigDecimal("500000"),
                20,
                new BigDecimal("350000"), // exceeds home value
                new BigDecimal("300000")
        );

        assertThatThrownBy(() -> validator.validate(request))
                .isInstanceOf(MortgageProcessingException.class)
                .hasMessage("Loan value cannot exceed the home value");
    }

    @Test
    void shouldPassValidationForValidRequest() {
        var request = new MortgageCheckRequest(
                new BigDecimal("50000"),
                20,
                new BigDecimal("150000"), // valid
                new BigDecimal("300000")
        );

        // Should not throw any exception
        validator.validate(request);
    }
}
