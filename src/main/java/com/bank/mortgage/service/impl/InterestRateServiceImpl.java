package com.bank.mortgage.service.impl;

import com.bank.mortgage.dto.InterestRateResponse;
import com.bank.mortgage.service.InterestRateService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class InterestRateServiceImpl implements InterestRateService {

    private final List<InterestRateResponse> rates = new ArrayList<>();

    @PostConstruct
    public void init() {
        // initialize in-memory list on startup
        rates.add(new InterestRateResponse(10, new BigDecimal("3.25"), Instant.now()));
        rates.add(new InterestRateResponse(15, new BigDecimal("3.75"), Instant.now()));
        rates.add(new InterestRateResponse(20, new BigDecimal("4.10"), Instant.now()));
        rates.add(new InterestRateResponse(30, new BigDecimal("4.50"), Instant.now()));
    }


    @Override
    public List<InterestRateResponse> getCurrentInterestRates() {
        return rates;

    }

}
