package com.github.ser.controller;

import com.github.ser.model.database.SponsorshipPackage;
import com.github.ser.model.lists.SponsorshipPackageListResponse;
import com.github.ser.model.requests.ChangeSponsorshipPackageRequest;
import com.github.ser.model.requests.CreateSponsorshipPackageRequest;
import com.github.ser.service.SponsorshipPackageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sponsorship-package")
@Log4j2
public class SponsorshipPackageController {

    private final SponsorshipPackageService sponsorshipPackageService;

    public SponsorshipPackageController(SponsorshipPackageService sponsorshipPackageService) {
        this.sponsorshipPackageService = sponsorshipPackageService;
    }


    @GetMapping
    public ResponseEntity<SponsorshipPackageListResponse> getAllSponsorshipPackages() {
        log.info("Getting all sponsorship packages");
        return new ResponseEntity<>(sponsorshipPackageService.getAllSponsorshipPackages(), HttpStatus.OK);
    }

    @GetMapping("/{sponsorshipPackageId}")
    public ResponseEntity<SponsorshipPackage> getSponsorshipPackageById(@PathVariable UUID sponsorshipPackageId) {
        log.info("Getting sponsorship package by id: " + sponsorshipPackageId);
        return new ResponseEntity<>(sponsorshipPackageService.getSponsorshipPackageById(sponsorshipPackageId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SponsorshipPackage> addNewSponsorshipPackage(@RequestBody CreateSponsorshipPackageRequest createSponsorshipPackageRequest) {
        log.info("Adding new sponsorship package");
        return new ResponseEntity<>(sponsorshipPackageService.addNewSponsorshipPackage(createSponsorshipPackageRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{sponsorshipPackageId}")
    public ResponseEntity<Void> deleteSponsorshipPackage(@PathVariable UUID sponsorshipPackageId) {
        log.info("Deleting sponsorship package: " + sponsorshipPackageId);
        sponsorshipPackageService.deleteSponsorshipPackage(sponsorshipPackageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{sponsorshipPackageId}")
    public ResponseEntity<SponsorshipPackage> changeSponsorshipPackageDetails(@PathVariable UUID sponsorshipPackageId, @RequestBody ChangeSponsorshipPackageRequest changeSponsorshipPackageRequest){
        log.info("Changing sponsorship package details: " + sponsorshipPackageId);
        return new ResponseEntity<>(sponsorshipPackageService.changeSponsorshipPackageDetails(sponsorshipPackageId, changeSponsorshipPackageRequest), HttpStatus.OK);

    }

}
