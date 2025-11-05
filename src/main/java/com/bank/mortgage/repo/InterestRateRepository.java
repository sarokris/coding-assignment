package com.bank.mortgage.repo;

import com.bank.mortgage.entity.InterestRates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterestRateRepository extends JpaRepository<InterestRates, Long> {
    Optional<InterestRates> findByMaturityPeriod(int maturityPeriod);
}
