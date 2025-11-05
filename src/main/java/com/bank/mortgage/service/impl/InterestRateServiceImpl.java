package com.bank.mortgage.service.impl;

import com.bank.mortgage.dto.InterestRateResponse;
import com.bank.mortgage.entity.InterestRates;
import com.bank.mortgage.exception.InterestRateNotFoundException;
import com.bank.mortgage.mapper.InterestRateMapper;
import com.bank.mortgage.repo.InterestRateRepository;
import com.bank.mortgage.service.InterestRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestRateServiceImpl implements InterestRateService {

    private final InterestRateRepository repository;
    private final InterestRateMapper interestRateMapper;


    @CachePut(value = "interestRates", key = "iRates", unless = "#result == null")
    @Override
    public List<InterestRateResponse> getCurrentInterestRates() {
        List<InterestRates> interestRates = repository.findAll();
        if(CollectionUtils.isEmpty(interestRates))
            throw new InterestRateNotFoundException("No interest rates found in the DB");
        return interestRates.stream().map(this::mapResponse).toList();

    }

    @Override
    public InterestRateResponse findByMaturityPeriod(int maturityPeriod) {
        return repository.findByMaturityPeriod(maturityPeriod)
                .map(this::mapResponse)
                .orElseThrow(() -> new InterestRateNotFoundException("No interest rate found for maturity period " + maturityPeriod));
    }

    /**
     * Quick workaround since Mapstruct Mapper does not seems to be working
     * TODO needs to move this logic if mapper issue resolved
     * @param iRate entity
     * @return Dto
     */
    private InterestRateResponse mapResponse(InterestRates iRate){
        InterestRateResponse dto = interestRateMapper.toDto(iRate);
        if(dto.maturityPeriod() == 0 && dto.interestRate() == null) {
            dto = new InterestRateResponse(iRate.getMaturityPeriod(),iRate.getInterestRate(),iRate.getLastUpdate());
        }
        return dto;
    }

}
