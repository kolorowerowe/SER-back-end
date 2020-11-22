package com.github.ser.service;

import com.github.ser.exception.badRequest.NoSPEquipmentException;
import com.github.ser.exception.badRequest.NoSponsorshipPackageException;
import com.github.ser.model.database.Equipment;
import com.github.ser.model.database.SPEquipment;
import com.github.ser.model.database.SponsorshipPackage;
import com.github.ser.model.lists.SponsorshipPackageListResponse;
import com.github.ser.model.requests.AddEquipmentToSponsorshipPackageRequest;
import com.github.ser.model.requests.ChangeSPEquipmentCountRequest;
import com.github.ser.model.requests.ChangeSponsorshipPackageRequest;
import com.github.ser.model.requests.CreateSponsorshipPackageRequest;
import com.github.ser.model.response.SponsorshipPackageResponse;
import com.github.ser.repository.SPEquipmentRepository;
import com.github.ser.repository.SponsorshipPackageRepository;
import com.github.ser.util.ModelUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class SponsorshipPackageService {

    private final SponsorshipPackageRepository sponsorshipPackageRepository;
    private final SPEquipmentRepository spEquipmentRepository;

    private final EquipmentService equipmentService;

    public SponsorshipPackageService(SponsorshipPackageRepository sponsorshipPackageRepository, SPEquipmentRepository spEquipmentRepository, EquipmentService equipmentService) {
        this.sponsorshipPackageRepository = sponsorshipPackageRepository;
        this.spEquipmentRepository = spEquipmentRepository;
        this.equipmentService = equipmentService;
    }


    public SponsorshipPackageResponse getSponsorshipPackageResponseById(UUID sponsorshipPackageId) {
        SponsorshipPackage sponsorshipPackage = getSponsorshipPackageById(sponsorshipPackageId);
        return getSponsorshipPackageResponse(sponsorshipPackage);
    }

    public SponsorshipPackage getSponsorshipPackageById(UUID sponsorshipPackageId) {
        return sponsorshipPackageRepository
                .findById(sponsorshipPackageId)
                .orElseThrow(
                        () -> new NoSponsorshipPackageException("No sponsorship package found: " + sponsorshipPackageId)
                );
    }


    public SPEquipment getSPEquipmentById(UUID spEquipmentId) {
        return spEquipmentRepository
                .findById(spEquipmentId)
                .orElseThrow(
                        () -> new NoSPEquipmentException("No sponsorship package equipment found: " + spEquipmentId)
                );
    }

    public SponsorshipPackageListResponse getAllSponsorshipPackages() {
        List<SponsorshipPackageResponse> sponsorshipPackageList = sponsorshipPackageRepository
                .findAll()
                .stream()
                .map(this::getSponsorshipPackageResponse)
                .sorted(Comparator.comparing(SponsorshipPackageResponse::getMaxCompanies))
                .collect(Collectors.toList());

        return SponsorshipPackageListResponse.builder()
                .sponsorshipPackageList(sponsorshipPackageList)
                .count(sponsorshipPackageList.size())
                .build();
    }

    public SponsorshipPackageResponse addNewSponsorshipPackage(CreateSponsorshipPackageRequest createSponsorshipPackageRequest) {
        SponsorshipPackage newSponsorshipPackage = SponsorshipPackage.builder()
                .translations(createSponsorshipPackageRequest.getTranslations())
                .prices(createSponsorshipPackageRequest.getPrices())
                .standSize(createSponsorshipPackageRequest.getStandSize())
                .maxCompanies(createSponsorshipPackageRequest.getMaxCompanies())
                .isAvailable(false)
                .companies(Collections.emptyList())
                .build();

        return getSponsorshipPackageResponse(sponsorshipPackageRepository.save(newSponsorshipPackage));
    }

    public SponsorshipPackageResponse changeSponsorshipPackageDetails(UUID companyId, ChangeSponsorshipPackageRequest changeSponsorshipPackageRequest) {
        SponsorshipPackage sponsorshipPackage = getSponsorshipPackageById(companyId);

        SponsorshipPackage updatedSponsorshipPackage = ModelUtils.copySponsorshipPackageNonNullProperties(sponsorshipPackage, changeSponsorshipPackageRequest);

        return getSponsorshipPackageResponse(sponsorshipPackageRepository.save(updatedSponsorshipPackage));
    }

    public void deleteSponsorshipPackage(UUID sponsorshipPackageId) {
        sponsorshipPackageRepository.deleteById(sponsorshipPackageId);
    }

    public SponsorshipPackageResponse addEquipmentToSponsorshipPackage(UUID sponsorshipPackageId, AddEquipmentToSponsorshipPackageRequest addEquipmentToSponsorshipPackageRequest) {
        //check if sponsorship package exists
        SponsorshipPackage sponsorshipPackage = getSponsorshipPackageById(sponsorshipPackageId);

        // check if equipment exists
        Equipment equipment = equipmentService.getEquipmentById(addEquipmentToSponsorshipPackageRequest.getEquipmentId());

        spEquipmentRepository.save(
                SPEquipment.builder()
                        .equipment(equipment)
                        .sponsorshipPackageId(sponsorshipPackageId)
                        .count(addEquipmentToSponsorshipPackageRequest.getCount())
                        .build()
        );

        return getSponsorshipPackageResponse(sponsorshipPackage);
    }

    public SponsorshipPackageResponse changeCountOfSPEquipment(UUID sponsorshipPackageId, UUID spEquipmentId, ChangeSPEquipmentCountRequest changeSPEquipmentCountRequest) {
        //check if sponsorship package exists
        SPEquipment spEquipment = getSPEquipmentById(spEquipmentId);

        spEquipment.setCount(changeSPEquipmentCountRequest.getCount());
        spEquipmentRepository.save(spEquipment);

        return getSponsorshipPackageResponseById(sponsorshipPackageId);
    }


    public SponsorshipPackageResponse removeEquipmentFromSponsorshipPackage(UUID sponsorshipPackageId, UUID spEquipmentId) {

        spEquipmentRepository.deleteById(spEquipmentId);

        return getSponsorshipPackageResponseById(sponsorshipPackageId);
    }

    private SponsorshipPackageResponse getSponsorshipPackageResponse(SponsorshipPackage sponsorshipPackage) {
        log.info("SP: " + sponsorshipPackage.getId() + " has " + sponsorshipPackage.getCompanies().size() + " companies");
        return SponsorshipPackageResponse.builder()
                .id(sponsorshipPackage.getId())
                .isAvailable(sponsorshipPackage.getIsAvailable())
                .prices(sponsorshipPackage.getPrices())
                .translations(sponsorshipPackage.getTranslations())
                .standSize(sponsorshipPackage.getStandSize())
                .maxCompanies(sponsorshipPackage.getMaxCompanies())
                .currentCompanies(sponsorshipPackage.getCompanies().size())
                .spEquipmentList(getEquipmentsForSponsorshipPackage(sponsorshipPackage.getId()))
                .build();
    }


    //SP equipment
    public List<SPEquipment> getEquipmentsForSponsorshipPackage(UUID sponsorshipPackageId) {

        return spEquipmentRepository.findAllBySponsorshipPackageId(sponsorshipPackageId);
    }

}
