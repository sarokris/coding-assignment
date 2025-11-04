package com.bank.mortgage.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record InterestRateResponse(
    int maturityPeriod,
    BigDecimal interestRate,   // e.g. 3.75 (percent)
    Instant lastUpdate
) {}
