package com.bank.mortgage.config;

import com.bank.mortgage.entity.InterestRates;
import com.bank.mortgage.repo.InterestRateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final InterestRateRepository repository;

    public DataInitializer(InterestRateRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        List<InterestRates> rates = List.of(
                createRate(10, "3.25")
                , createRate(15, "3.75")
                , createRate(20, "4.10")
        );
        log.info("data loaded");
        repository.saveAll(rates);
    }

    private InterestRates createRate(int maturityPeriod, String interestRate) {
        InterestRates iRate = new InterestRates();
        iRate.setMaturityPeriod(maturityPeriod);
        iRate.setInterestRate(new BigDecimal(interestRate));
        iRate.setLastUpdate(Instant.now());
        return iRate;
    }
}
