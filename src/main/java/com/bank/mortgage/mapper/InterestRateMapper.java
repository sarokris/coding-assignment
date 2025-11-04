package com.bank.mortgage.mapper;

import com.bank.mortgage.dto.InterestRateResponse;
import com.bank.mortgage.entity.InterestRates;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InterestRateMapper {

    InterestRateResponse toDto(InterestRates entity);
}