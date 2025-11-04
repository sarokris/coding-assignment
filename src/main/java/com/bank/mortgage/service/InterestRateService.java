package com.bank.mortgage.service;

import com.bank.mortgage.dto.InterestRateResponse;

import java.util.List;

public interface InterestRateService {
    List<InterestRateResponse> getCurrentInterestRates();
}
