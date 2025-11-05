package com.bank.mortgage.service.impl;

import com.bank.mortgage.dto.InterestRateResponse;
import com.bank.mortgage.dto.MortgageCheckRequest;
import com.bank.mortgage.dto.MortgageCheckResponse;
import com.bank.mortgage.exception.InterestRateNotFoundException;
import com.bank.mortgage.exception.MortgageProcessingException;
import com.bank.mortgage.service.InterestRateService;
import com.bank.mortgage.validator.MortgageValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class MortgageServiceImpTest {

    @Mock
    private InterestRateService interestRateService;

    @Mock
    private MortgageValidator mortgageValidator;

    @InjectMocks
    private MortgageServiceImpl mortgageService;

    @Test
    void shouldCalculateMonthlyPaymentCorrectly() {
        MortgageCheckRequest request = new MortgageCheckRequest(
                new BigDecimal("50000"),
                20,
                new BigDecimal("200000"),
                new BigDecimal("250000")
        );

        InterestRateResponse interestRate = new InterestRateResponse(20,new BigDecimal("0.05"), Instant.now());

        when(interestRateService.findByMaturityPeriod(20)).thenReturn(interestRate);

        MortgageCheckResponse response = mortgageService.performMortgageCheck(request);

        // Monthly payment ≈ 1319.91
        BigDecimal expectedMonthly = new BigDecimal("1319.91");

        // Assert that monthly payment is close to expected value (round to 2 decimals)
        BigDecimal actualRounded = response.monthlyCost().setScale(2, RoundingMode.HALF_UP);
        assertThat(actualRounded).isEqualTo(expectedMonthly);

        // Other fields
        assertTrue(response.feasible());

        verify(interestRateService, times(1)).findByMaturityPeriod(20);
    }

    @Test
    void shouldThrowExceptionWhenInterestRateNotFound() {
        MortgageCheckRequest request = new MortgageCheckRequest(
                new BigDecimal("50000"),
                25,
                new BigDecimal("200000"),
                new BigDecimal("250000")
        );

        String errMsg = "No interest rate found for maturity period 25";
        when(interestRateService.findByMaturityPeriod(25)).thenThrow(new InterestRateNotFoundException(errMsg));

        assertThatThrownBy(() -> mortgageService.performMortgageCheck(request))
                .isInstanceOf(InterestRateNotFoundException.class)
                .hasMessageContaining(errMsg);

        verify(interestRateService, times(1)).findByMaturityPeriod(25);
    }

    @Test
    void shouldThrowExceptionWhenForValidation() {
        MortgageCheckRequest request = new MortgageCheckRequest(
                new BigDecimal("1000"),
                25,
                new BigDecimal("200000"),
                new BigDecimal("250000")
        );

        String errorMessage = "Loan value cannot exceed 4 times the income";
        doThrow(new MortgageProcessingException(errorMessage))
                .when(mortgageValidator)
                .validate(request);

        assertThatThrownBy(() -> mortgageService.performMortgageCheck(request))
                .isInstanceOf(MortgageProcessingException.class)
                .hasMessageContaining(errorMessage);

        verify(interestRateService, never()).findByMaturityPeriod(25);
    }
}
