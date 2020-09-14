package com.github.ser.service;

import com.github.ser.exception.badRequest.NoSponsorshipPackageException;
import com.github.ser.model.database.SponsorshipPackage;
import com.github.ser.model.lists.SponsorshipPackageListResponse;
import com.github.ser.model.requests.ChangeSponsorshipPackageRequest;
import com.github.ser.model.requests.CreateSponsorshipPackageRequest;
import com.github.ser.repository.SponsorshipPackageRepository;
import com.github.ser.util.ModelUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class SponsorshipPackageService {

    private final SponsorshipPackageRepository sponsorshipPackageRepository;

    public SponsorshipPackageService(SponsorshipPackageRepository sponsorshipPackageRepository) {
        this.sponsorshipPackageRepository = sponsorshipPackageRepository;
    }

    public SponsorshipPackage getSponsorshipPackageById(UUID sponsorshipPackageId) {
        SponsorshipPackage sponsorshipPackage = sponsorshipPackageRepository.findById(sponsorshipPackageId).orElse(null);
        if (sponsorshipPackage == null) {
            log.debug("Sponsorship package: " + sponsorshipPackageId + " does not exist");
            throw new NoSponsorshipPackageException("No sponsorship package found: " + sponsorshipPackageId);
        }
        return sponsorshipPackage;
    }

    public SponsorshipPackageListResponse getAllSponsorshipPackages() {
        List<SponsorshipPackage> sponsorshipPackageList = sponsorshipPackageRepository.findAll();
        sponsorshipPackageList.sort(Comparator.comparing(SponsorshipPackage::getStandSize).reversed());

        return SponsorshipPackageListResponse.builder()
                .sponsorshipPackageList(sponsorshipPackageList)
                .count(sponsorshipPackageList.size())
                .build();
    }

    public SponsorshipPackage addNewSponsorshipPackage(CreateSponsorshipPackageRequest createSponsorshipPackageRequest) {
        SponsorshipPackage newSponsorshipPackage = SponsorshipPackage.builder()
                .translations(createSponsorshipPackageRequest.getTranslations())
                .prices(createSponsorshipPackageRequest.getPrices())
                .standSize(createSponsorshipPackageRequest.getStandSize())
                .isAvailable(false)
                .build();

        return sponsorshipPackageRepository.save(newSponsorshipPackage);
    }

    public SponsorshipPackage changeSponsorshipPackageDetails(UUID companyId, ChangeSponsorshipPackageRequest changeSponsorshipPackageRequest) {
        SponsorshipPackage sponsorshipPackage = getSponsorshipPackageById(companyId);

        SponsorshipPackage updatedSponsorshipPackage = ModelUtils.copySponsorshipPackageNonNullProperties(sponsorshipPackage, changeSponsorshipPackageRequest);

        return sponsorshipPackageRepository.save(updatedSponsorshipPackage);
    }

    public void deleteSponsorshipPackage(UUID sponsorshipPackageId) {
        sponsorshipPackageRepository.deleteById(sponsorshipPackageId);
    }
}
