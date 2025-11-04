package com.bank.mortgage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
public class MortgageRate {
    @Id
    private Integer maturityPeriod;
    private BigDecimal interestRate;
    private Instant lastUpdate;
}