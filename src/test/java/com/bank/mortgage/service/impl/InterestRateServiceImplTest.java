package com.bank.mortgage.service.impl;

import com.bank.mortgage.dto.InterestRateResponse;
import com.bank.mortgage.entity.InterestRates;
import com.bank.mortgage.exception.InterestRateNotFoundException;
import com.bank.mortgage.mapper.InterestRateMapper;
import com.bank.mortgage.repo.InterestRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InterestRateServiceImplTest {

    @Mock
    private InterestRateRepository repository;

    @Mock
    private InterestRateMapper interestRateMapper;

    @InjectMocks
    private InterestRateServiceImpl interestRateService;


    private InterestRates entity1;
    private InterestRates entity2;

    private InterestRateResponse response1;
    private InterestRateResponse response2;

    @BeforeEach
    void setUp() {
        // Initialize sample entities
        entity1 = new InterestRates();
        entity1.setId(1L);
        entity1.setMaturityPeriod(10);
        entity1.setInterestRate(new BigDecimal("3.25"));

        entity2 = new InterestRates();
        entity2.setId(2L);
        entity2.setMaturityPeriod(15);
        entity2.setInterestRate(new BigDecimal("3.75"));

        // Initialize sample records/response DTOs
        response1 = new InterestRateResponse(10, new BigDecimal("3.25"), Instant.now());
        response2 = new InterestRateResponse(15,  new BigDecimal("3.75"), Instant.now());
    }

    @Test
    @DisplayName("Should return a list of InterestRateResponse when rates exist")
    void getCurrentInterestRates_Success() {
        // ARRANGE
        List<InterestRates> entities = Arrays.asList(entity1, entity2);

        // Define mock behavior for the repository
        when(repository.findAll()).thenReturn(entities);

        // Define mock behavior for the mapper for EACH entity
        when(interestRateMapper.toDto(entity1)).thenReturn(response1);
        when(interestRateMapper.toDto(entity2)).thenReturn(response2);

        // ACT
        List<InterestRateResponse> actualResponses = interestRateService.getCurrentInterestRates();

        // ASSERT
        assertNotNull(actualResponses, "The returned list should not be null");
        assertEquals(2, actualResponses.size(), "The list size should match the number of entities");
        assertEquals(response1, actualResponses.get(0), "The first mapped DTO should be correct");
        assertEquals(response2, actualResponses.get(1), "The second mapped DTO should be correct");

        // VERIFY: Ensure the repository was called exactly once
        verify(repository, times(1)).findAll();
        // VERIFY: Ensure the mapper was called once for each entity
        verify(interestRateMapper, times(1)).toDto(entity1);
        verify(interestRateMapper, times(1)).toDto(entity2);
    }


    @Test
    @DisplayName("Should handle a null result from the repository gracefully")
    void getCurrentInterestRates_NullRepositoryResult() {
        // ARRANGE
        // Mocking findAll to return null (though modern repositories rarely do this)
        when(repository.findAll()).thenReturn(null);



        // Given the standard Java streams implementation:
        assertThrows(InterestRateNotFoundException.class, () -> interestRateService.getCurrentInterestRates(),
                "InterestRateNotFoundException should be thrown when no data retrieved from DB");

        // VERIFY
        verify(repository, times(1)).findAll();
        verify(interestRateMapper, never()).toDto(any());
    }

    @Test
    @DisplayName(" Expect the InterestRateNotFoundException when calling findByMaturityPeriod")
    void testFindInterestRateByMaturityPeriod() {
        int maturityPeriod = 10;
        when(repository.findByMaturityPeriod(eq(maturityPeriod))).thenReturn(Optional.empty());
        assertThrows(InterestRateNotFoundException.class, () -> interestRateService.findByMaturityPeriod(maturityPeriod),
                "No interest rate found for maturity period "+maturityPeriod);

        // VERIFY
        verify(repository, times(1)).findByMaturityPeriod(eq(maturityPeriod));
        verify(interestRateMapper, never()).toDto(any());
    }


}