package com.bank.mortgage.service;

import com.bank.mortgage.dto.MortgageCheckRequest;
import com.bank.mortgage.dto.MortgageCheckResponse;

public interface MortgageService {

    MortgageCheckResponse performMortgageCheck(MortgageCheckRequest request);
}
