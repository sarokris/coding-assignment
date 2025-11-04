package com.bank.mortgage.repo;

import com.bank.mortgage.entity.InterestRates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestRateRepository extends JpaRepository<InterestRates, Long> {
}
