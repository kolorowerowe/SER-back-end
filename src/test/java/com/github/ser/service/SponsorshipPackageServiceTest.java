package com.github.ser.service;

import com.github.ser.SerApplication;
import com.github.ser.exception.badRequest.NoSponsorshipPackageException;
import com.github.ser.model.database.Price;
import com.github.ser.model.database.SponsorshipPackage;
import com.github.ser.model.database.Translation;
import com.github.ser.model.lists.SponsorshipPackageListResponse;
import com.github.ser.model.requests.CreateSponsorshipPackageRequest;
import com.github.ser.model.response.SponsorshipPackageResponse;
import com.github.ser.repository.EquipmentRepository;
import com.github.ser.repository.SPEquipmentRepository;
import com.github.ser.repository.SponsorshipPackageRepository;
import com.github.ser.testutils.PopulateDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static com.github.ser.testutils.PopulateDatabase.populateSponsorshipPackageRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Sponsorship package service tests")
class SponsorshipPackageServiceTest {


    @Autowired
    private SponsorshipPackageRepository sponsorshipPackageRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private SPEquipmentRepository spEquipmentRepository;

    @Autowired
    private PopulateDatabase populateDatabase;


    private SponsorshipPackageService sponsorshipPackageService;

    private UUID sponsorshipPackageId;

    @BeforeEach
    void setup() {
        initMocks(this);
        equipmentRepository.deleteAll();
        sponsorshipPackageId = populateSponsorshipPackageRepository(sponsorshipPackageRepository).getId();
        populateDatabase.populateEquipmentRepository(spEquipmentRepository, equipmentRepository, sponsorshipPackageId).get(0);

        EquipmentService equipmentService = new EquipmentService(equipmentRepository);
        sponsorshipPackageService = new SponsorshipPackageService(sponsorshipPackageRepository, spEquipmentRepository, equipmentService);
    }

    @AfterEach
    void clear() {
        sponsorshipPackageRepository.deleteAll();

        spEquipmentRepository.deleteAll();
        equipmentRepository.deleteAll();
    }

    @Test
    @DisplayName("Get sponsorship package by id - return NoSponsorshipPackageException")
    void getSponsorshipPackageById_throwNoSponsorshipPackageException() {

        UUID notExistingCompanyUuid = UUID.randomUUID();

        assertThrows(NoSponsorshipPackageException.class, () -> sponsorshipPackageService.getSponsorshipPackageById(notExistingCompanyUuid));
    }


    @Test
    @DisplayName("Get sponsorship package by id - return SponsorshipPackage")
    void getSponsorshipPackageById_returnCompany() {


        SponsorshipPackage sponsorshipPackage = sponsorshipPackageService.getSponsorshipPackageById(sponsorshipPackageId);

        assertAll(
                () -> assertNotNull(sponsorshipPackage),
                () -> assertEquals(sponsorshipPackageId, sponsorshipPackage.getId()),
                () -> assertEquals("Sponsor główny", sponsorshipPackage.getTranslations().stream().findFirst().get().getName())
        );
    }

    @Test
    @DisplayName("Get sponsorship package response by id - return SponsorshipPackageResponse")
    void getSponsorshipPackageResponseById_returnSponsorshipPackageResponse() {


        SponsorshipPackageResponse sponsorshipPackageResponse = sponsorshipPackageService.getSponsorshipPackageResponseById(sponsorshipPackageId);

        assertAll(
                () -> assertNotNull(sponsorshipPackageResponse),
                () -> assertEquals(sponsorshipPackageId, sponsorshipPackageResponse.getId()),
                () -> assertEquals("Sponsor główny", sponsorshipPackageResponse.getTranslations().stream().findFirst().get().getName()),
                () -> assertEquals(2, sponsorshipPackageResponse.getSpEquipmentList().get(0).getCount())
        );
    }

    @Test
    @DisplayName("Get all sponsorship packages - return SponsorshipPackageListResponse")
    void getAllSponsorshipPackages_returnSponsorshipPackageListResponse() {


        SponsorshipPackageListResponse sponsorshipPackageListResponse = sponsorshipPackageService.getAllSponsorshipPackages();

        assertAll(
                () -> assertNotNull(sponsorshipPackageListResponse),
                () -> assertEquals(1, sponsorshipPackageListResponse.getCount()),
                () -> assertTrue(sponsorshipPackageListResponse
                        .getSponsorshipPackageList()
                        .get(0)
                        .getTranslations()
                        .stream()
                        .anyMatch(translation -> translation
                                .getName()
                                .equals("Sponsor główny")))

        );
    }

    @Test
    @DisplayName("Add new sponsorship package - return SponsorshipPackageResponse")
    void addNewSponsorshipPackage_returnSponsorshipPackageResponse() {

        CreateSponsorshipPackageRequest createSponsorshipPackageRequest = CreateSponsorshipPackageRequest.builder()
                .translations(Set.of(Translation.builder()
                        .name("nowy")
                        .description("nowy opis")
                        .languageCode("pl")
                        .build()))
                .prices(Set.of(Price.builder()
                        .value(BigDecimal.valueOf(12.0))
                        .currency("PLN")
                        .build()))
                .standSize(12.0)
                .build();

        SponsorshipPackageResponse sponsorshipPackageResponse = sponsorshipPackageService.addNewSponsorshipPackage(createSponsorshipPackageRequest);

        assertAll(
                () -> assertNotNull(sponsorshipPackageResponse),
                () -> assertTrue(sponsorshipPackageResponse
                        .getTranslations()
                        .stream()
                        .anyMatch(translation -> translation
                                .getName()
                                .equals("nowy"))),
                () -> assertTrue(sponsorshipPackageResponse.getSpEquipmentList().isEmpty())

        );
    }


}