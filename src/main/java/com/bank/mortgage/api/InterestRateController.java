package com.bank.mortgage.api;

import com.bank.mortgage.dto.InterestRateResponse;
import com.bank.mortgage.service.InterestRateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name="InterestRate")
public class InterestRateController {

    private final InterestRateService interestRateService;

    @GetMapping("/interest-rates")
    public ResponseEntity<List<InterestRateResponse>> getRates() {
        List<InterestRateResponse> page = interestRateService.getCurrentInterestRates();
        return ResponseEntity.ok(page);
    }
}
