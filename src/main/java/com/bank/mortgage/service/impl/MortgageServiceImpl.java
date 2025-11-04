package com.bank.mortgage.service.impl;

import com.bank.mortgage.dto.MortgageCheckRequest;
import com.bank.mortgage.dto.MortgageCheckResponse;
import com.bank.mortgage.service.MortgageService;
import com.bank.mortgage.validator.MortgageValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MortgageServiceImpl implements MortgageService {

    private final MortgageValidator mortgageValidator;

    @Override
    public MortgageCheckResponse performMortgageCheck(MortgageCheckRequest request) {
        mortgageValidator.validate(request);
        return null;
    }
}
