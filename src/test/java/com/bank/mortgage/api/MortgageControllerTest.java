package com.bank.mortgage.api;

import com.bank.mortgage.dto.MortgageCheckRequest;
import com.bank.mortgage.service.MortgageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MortgageController.class)
class MortgageControllerTest {

    public static final String PATH = "/api/mortgage-check";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MortgageService service; // mock service

    public static Stream<Arguments> buildInvalidIncomeRequest() {
        MortgageCheckRequest nullINcomeReq = new MortgageCheckRequest(
                null, // income missing
                20,
                new BigDecimal("150000"),
                new BigDecimal("300000")
        );

        MortgageCheckRequest negativeINcomeReq = new MortgageCheckRequest(
                new BigDecimal("-1"), // income missing
                20,
                new BigDecimal("150000"),
                new BigDecimal("300000")
        );
        return Stream.of(Arguments.of(nullINcomeReq,"Income is required")
                ,Arguments.of(negativeINcomeReq,"Income must be greater than 0"));
    }

    public static Stream<Arguments> buildInvalidLoanValueRequest() {
        MortgageCheckRequest nullINcomeReq = new MortgageCheckRequest(
                new BigDecimal("300000"), // income missing
                20,
                null,
                new BigDecimal("300000")
        );

        MortgageCheckRequest negativeINcomeReq = new MortgageCheckRequest(
                new BigDecimal("1.00"), // income missing
                20,
                new BigDecimal("-1500"),
                new BigDecimal("300000")
        );
        return Stream.of(Arguments.of(nullINcomeReq,"Loan value is required")
                ,Arguments.of(negativeINcomeReq,"Loan value must be greater than 0"));
    }

    @ParameterizedTest
    @MethodSource("buildInvalidIncomeRequest")
    void shouldReturn400WhenInvalidIncome(MortgageCheckRequest request,String errMsg) throws Exception {
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.income").value(errMsg));
    }

    @ParameterizedTest
    @MethodSource("buildInvalidLoanValueRequest")
    void shouldReturn400WhenInvalidLoanValue(MortgageCheckRequest request,String errMsg) throws Exception {
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.loanValue").value(errMsg));
    }

    @Test
    void shouldReturn200ForValidRequest() throws Exception {
        var request = new MortgageCheckRequest(
                new BigDecimal("50000"),
                20,
                new BigDecimal("150000"),
                new BigDecimal("300000")
        );

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.feasible").value(true));
    }
}
