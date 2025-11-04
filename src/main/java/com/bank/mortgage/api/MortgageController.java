package com.bank.mortgage.api;

import com.bank.mortgage.dto.MortgageCheckRequest;
import com.bank.mortgage.dto.MortgageCheckResponse;
import com.bank.mortgage.service.MortgageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MortgageController {

    private final MortgageService mortgageService;

    @PostMapping("/mortgage-check")
    public ResponseEntity<MortgageCheckResponse> checkMortgage(@RequestBody @Valid MortgageCheckRequest mortCheckReq) {
        return  ResponseEntity.ok(mortgageService.performMortgageCheck(mortCheckReq));
    }

}
