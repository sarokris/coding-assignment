package com.bank.mortgage.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MortgageController {

    @GetMapping("/interest-rates")
    public ResponseEntity<String> getRates() {
        throw new UnsupportedOperationException(" Yet to be implemented");
    }

}
