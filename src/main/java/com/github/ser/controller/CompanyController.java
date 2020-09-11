package com.github.ser.controller;

import com.github.ser.model.lists.CompanyListResponse;
import com.github.ser.model.requests.ChangeCompanyDetailsRequest;
import com.github.ser.model.requests.CreateCompanyRequest;
import com.github.ser.model.response.CompanyResponse;
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

    @GetMapping(params = "userId")
    public ResponseEntity<CompanyListResponse> getCompaniesForUser(@RequestParam UUID userId) {
        log.info("Getting companies for user: " + userId);
        return new ResponseEntity<>(companyService.getCompaniesForUser(userId), HttpStatus.OK);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable UUID companyId) {
        log.info("Getting company by id: " + companyId);
        return new ResponseEntity<>(companyService.getCompanyResponseById(companyId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CompanyResponse> registerNewCompany(@RequestBody CreateCompanyRequest createCompanyRequest) {
        log.info("Register new company: " + createCompanyRequest.getName());
        return new ResponseEntity<>(companyService.createNewCompany(createCompanyRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompanyById(@PathVariable UUID companyId) {
        log.info("Deleting company by id: " + companyId);
        companyService.deleteCompanyById(companyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> changeCompanyDetails(@PathVariable UUID companyId, @RequestBody ChangeCompanyDetailsRequest changeCompanyDetailsRequest){
        log.info("Changing company details: " + companyId);
        return new ResponseEntity<>(companyService.changeCompanyDetails(companyId, changeCompanyDetailsRequest), HttpStatus.OK);

    }

    @PatchMapping("/{companyId}/sponsorship-package/{sponsorshipPackageId}")
    public ResponseEntity<CompanyResponse> changeCompanyDetails(@PathVariable UUID companyId, @PathVariable UUID sponsorshipPackageId){
        log.info("Setting sponsorship package: " + sponsorshipPackageId + " for company: " + companyId);
        return new ResponseEntity<>(companyService.setSponsorshipPackage(companyId, sponsorshipPackageId), HttpStatus.OK);

    }


}
