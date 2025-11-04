package com.bank.mortgage.api;

import com.bank.mortgage.dto.MortgageCheckRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MortgageController {

    @GetMapping("/mortgage-check")
    public ResponseEntity<String> checkMortgage(@RequestBody @Valid MortgageCheckRequest mortCheckReq) {
        throw new UnsupportedOperationException(" Yet to be implemented");
    }

}
