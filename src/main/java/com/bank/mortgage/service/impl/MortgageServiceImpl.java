package com.bank.mortgage.service.impl;

import com.bank.mortgage.dto.InterestRateResponse;
import com.bank.mortgage.dto.MortgageCheckRequest;
import com.bank.mortgage.dto.MortgageCheckResponse;
import com.bank.mortgage.service.InterestRateService;
import com.bank.mortgage.service.MortgageService;
import com.bank.mortgage.validator.MortgageValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class MortgageServiceImpl implements MortgageService {

    private final MortgageValidator mortgageValidator;
    private final InterestRateService interestRateService;

    @Override
    public MortgageCheckResponse performMortgageCheck(MortgageCheckRequest request) {
        mortgageValidator.validate(request);

        InterestRateResponse interestRate = interestRateService.findByMaturityPeriod(request.maturityPeriod());

        BigDecimal annualInterestRate = interestRate.interestRate();
        BigDecimal monthlyPayment = getMonthlyPayment(request, annualInterestRate).setScale(2, RoundingMode.HALF_UP);

        return new MortgageCheckResponse(true,monthlyPayment);
    }

    private static BigDecimal getMonthlyPayment(MortgageCheckRequest request, BigDecimal annualInterestRate) {
        BigDecimal monthlyInterestRate = annualInterestRate.divide(BigDecimal.valueOf(12), MathContext.DECIMAL128);

        int months = request.maturityPeriod() * 12;
        BigDecimal loan = request.loanValue();

        BigDecimal numerator = loan.multiply(monthlyInterestRate);
        BigDecimal denominator = BigDecimal.ONE.subtract(
                BigDecimal.ONE.add(monthlyInterestRate).pow(-months, MathContext.DECIMAL128)
        );

        return numerator.divide(denominator, MathContext.DECIMAL128);
    }
}
