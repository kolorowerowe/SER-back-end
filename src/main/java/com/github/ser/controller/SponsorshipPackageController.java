package com.github.ser.controller;

import com.github.ser.model.lists.SponsorshipPackageListResponse;
import com.github.ser.model.requests.AddEquipmentToSponsorshipPackageRequest;
import com.github.ser.model.requests.ChangeSPEquipmentCountRequest;
import com.github.ser.model.requests.ChangeSponsorshipPackageRequest;
import com.github.ser.model.requests.CreateSponsorshipPackageRequest;
import com.github.ser.model.response.SponsorshipPackageResponse;
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
    public ResponseEntity<SponsorshipPackageResponse> getSponsorshipPackageById(@PathVariable UUID sponsorshipPackageId) {
        log.info("Getting sponsorship package by id: " + sponsorshipPackageId);
        return new ResponseEntity<>(sponsorshipPackageService.getSponsorshipPackageResponseById(sponsorshipPackageId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SponsorshipPackageResponse> addNewSponsorshipPackage(@RequestBody CreateSponsorshipPackageRequest createSponsorshipPackageRequest) {
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
    public ResponseEntity<SponsorshipPackageResponse> changeSponsorshipPackageDetails(@PathVariable UUID sponsorshipPackageId, @RequestBody ChangeSponsorshipPackageRequest changeSponsorshipPackageRequest) {
        log.info("Changing sponsorship package details: " + sponsorshipPackageId);
        return new ResponseEntity<>(sponsorshipPackageService.changeSponsorshipPackageDetails(sponsorshipPackageId, changeSponsorshipPackageRequest), HttpStatus.OK);
    }

    @PostMapping("/{sponsorshipPackageId}/equipment")
    public ResponseEntity<SponsorshipPackageResponse> addEquipmentToSponsorshipPackage(@PathVariable UUID sponsorshipPackageId, @RequestBody AddEquipmentToSponsorshipPackageRequest request) {
        log.info("Adding equipment " + request.getEquipmentId() + " to sponsorship package " + sponsorshipPackageId);
        return new ResponseEntity<>(sponsorshipPackageService.addEquipmentToSponsorshipPackage(sponsorshipPackageId, request), HttpStatus.OK);
    }

    @PatchMapping("/{sponsorshipPackageId}/equipment/{spEquipmentId}")
    public ResponseEntity<SponsorshipPackageResponse> changeCountOfSPEquipment(@PathVariable UUID sponsorshipPackageId, @PathVariable UUID spEquipmentId, @RequestBody ChangeSPEquipmentCountRequest request) {
        log.info("Changing count of sp equipment " + sponsorshipPackageId);
        return new ResponseEntity<>(sponsorshipPackageService.changeCountOfSPEquipment(sponsorshipPackageId, spEquipmentId, request), HttpStatus.OK);
    }

    @DeleteMapping("/{sponsorshipPackageId}/equipment/{spEquipmentId}")
    public ResponseEntity<SponsorshipPackageResponse> removeEquipmentFromSponsorshipPackage(@PathVariable UUID sponsorshipPackageId, @PathVariable UUID spEquipmentId) {
        log.info("Removing spEquipment: " + sponsorshipPackageId);
        return new ResponseEntity<>(sponsorshipPackageService.removeEquipmentFromSponsorshipPackage(sponsorshipPackageId, spEquipmentId), HttpStatus.OK);
    }

}
