package com.github.ser.controller;

import com.github.ser.model.database.Company;
import com.github.ser.model.lists.CompanyListResponse;
import com.github.ser.model.requests.CreateCompanyRequest;
import com.github.ser.service.CompanyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/company")
@Log4j2
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<CompanyListResponse> getAllCompanies() {
        log.info("Getting all companies");
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<Company> getCompanyById(@PathVariable UUID companyId) {
        log.info("Getting company by uuid: " + companyId);
        return new ResponseEntity<>(companyService.getCompanyById(companyId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Company> registerNewCompany(@RequestBody CreateCompanyRequest createCompanyRequest) {
        log.info("Register new company: " + createCompanyRequest.getName());
        return new ResponseEntity<>(companyService.createNewCompany(createCompanyRequest), HttpStatus.OK);
    }

}
