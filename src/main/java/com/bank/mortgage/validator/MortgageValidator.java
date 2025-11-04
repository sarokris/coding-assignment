package com.bank.mortgage.validator;

import com.bank.mortgage.dto.MortgageCheckRequest;
import com.bank.mortgage.exception.MortgageProcessingException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MortgageValidator {

    public void validate(MortgageCheckRequest request) {
        BigDecimal maxLoanAllowed = request.income().multiply(BigDecimal.valueOf(4));

        if (request.loanValue().compareTo(maxLoanAllowed) > 0) {
            throw new MortgageProcessingException("Loan value cannot exceed 4 times the income");
        }

        if (request.loanValue().compareTo(request.homeValue()) > 0) {
            throw new MortgageProcessingException("Loan value cannot exceed the home value");
        }
    }
}
