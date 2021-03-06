package com.github.ser.controller;

import com.github.ser.model.lists.CompanyListResponse;
import com.github.ser.model.requests.ChangeCompanyDetailsRequest;
import com.github.ser.model.requests.CreateCompanyRequest;
import com.github.ser.model.response.CompanyResponse;
import com.github.ser.service.CompanyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @PreAuthorize("@accessVerificationBean.isAdminOrOrganizer()")
    public ResponseEntity<CompanyListResponse> getAllCompanies() {
        log.info("Getting all companies");
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @GetMapping("/{companyId}")
    @PreAuthorize("@accessVerificationBean.hasAccessToCompany(#companyId)")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable UUID companyId) {
        log.info("Getting company by id: " + companyId);
        return new ResponseEntity<>(companyService.getCompanyResponseById(companyId), HttpStatus.OK);
    }

    @GetMapping(params = "userId")
    public ResponseEntity<CompanyListResponse> getCompaniesForUser(@RequestParam UUID userId) {
        log.info("Getting companies for user: " + userId);
        return new ResponseEntity<>(companyService.getCompaniesForUser(userId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CompanyResponse> registerNewCompany(@RequestBody CreateCompanyRequest createCompanyRequest) {
        log.info("Register new company: " + createCompanyRequest.getName());
        return new ResponseEntity<>(companyService.createNewCompany(createCompanyRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{companyId}")
    @PreAuthorize("@accessVerificationBean.hasAccessToCompany(#companyId)")
    public ResponseEntity<Void> deleteCompanyById(@PathVariable UUID companyId) {
        log.info("Deleting company by id: " + companyId);
        companyService.deleteCompanyById(companyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{companyId}")
    @PreAuthorize("@accessVerificationBean.hasAccessToCompany(#companyId)")
    public ResponseEntity<CompanyResponse> changeCompanyDetails(@PathVariable UUID companyId, @RequestBody ChangeCompanyDetailsRequest changeCompanyDetailsRequest) {
        log.info("Changing company details: " + companyId);
        return new ResponseEntity<>(companyService.changeCompanyDetails(companyId, changeCompanyDetailsRequest), HttpStatus.OK);

    }

    @PatchMapping("/{companyId}/sponsorship-package/{sponsorshipPackageId}")
    @PreAuthorize("@accessVerificationBean.hasAccessToCompany(#companyId)")
    public ResponseEntity<CompanyResponse> setSponsorshipPackage(@PathVariable UUID companyId, @PathVariable UUID sponsorshipPackageId) {
        log.info("Setting sponsorship package: " + sponsorshipPackageId + " for company: " + companyId);
        return new ResponseEntity<>(companyService.setSponsorshipPackage(companyId, sponsorshipPackageId), HttpStatus.OK);

    }

    @GetMapping("/export")
    @PreAuthorize("@accessVerificationBean.isAdminOrOrganizer()")
    public ResponseEntity<String> exportCompaniesToCsv() {
        log.info("Exporting companies to csv");

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.TEXT_PLAIN)
                .header("x-suggested-filename", "companies_" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) + ".csv")
                .body(companyService.exportCompaniesToCsv());
    }

    @GetMapping("/export/catalog")
    @PreAuthorize("@accessVerificationBean.isAdminOrOrganizer()")
    public ResponseEntity<String> exportCatalogInformationToCsv() {
        log.info("Exporting catalog information to csv");

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.TEXT_PLAIN)
                .header("x-suggested-filename", "catalog_information_" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) + ".csv")
                .body(companyService.exportCatalogInformationToCsv());
    }

}
