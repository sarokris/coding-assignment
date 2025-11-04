package com.bank.mortgage.api;

import com.bank.mortgage.dto.InterestRateResponse;
import com.bank.mortgage.exception.InterestRateNotFoundException;
import com.bank.mortgage.exception.MortgageControllerAdvice;
import com.bank.mortgage.service.InterestRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({InterestRateController.class, MortgageControllerAdvice.class})
class InterestRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InterestRateService interestRateService;

    private final String API_URL = "/api/interest-rates";


    @Test
    @DisplayName("Should return 404 NOT FOUND when interest rates are not found")
    void getRates_shouldReturnNotFound_whenRatesDoNotExist() throws Exception {
        String expectedErrorMessage = "No interest rates found ";
        when(interestRateService.getCurrentInterestRates()).thenThrow(new InterestRateNotFoundException(expectedErrorMessage));
        ResultActions result = mockMvc.perform(get(API_URL)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound()) // 404
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage));

    }


    @Test
    @DisplayName("Should return 200 OK and expected rates on successful check")
    void getRates_shouldReturnOkAndPayload() throws Exception {
        // ARRANGE
        String successPayload = """
                {
                  "maturityPeriod": 10,
                  "interestRate": 3.25,
                  "lastUpdate": "2025-11-04T21:33:24Z"
                }
                """;

        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // 2. Register the module with the ObjectMapper

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(javaTimeModule);
        InterestRateResponse interestRateResponse = objectMapper.readValue(successPayload, InterestRateResponse.class);


        when(interestRateService.getCurrentInterestRates()).thenReturn(List.of(interestRateResponse));

        // ACT
        ResultActions result = mockMvc.perform(get(API_URL)
                .contentType(MediaType.APPLICATION_JSON));


        result.andExpect(status().isOk()) // 200 OK
              .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}