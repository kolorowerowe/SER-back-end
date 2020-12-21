package com.github.ser.service;

import com.github.ser.SerApplication;
import com.github.ser.exception.badRequest.NoEquipmentException;
import com.github.ser.model.database.Equipment;
import com.github.ser.model.lists.EquipmentListResponse;
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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.ser.testutils.PopulateDatabase.populateSponsorshipPackageRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Sponsorship package service tests")
class EquipmentServiceTest {


    @Autowired
    private SponsorshipPackageRepository sponsorshipPackageRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private SPEquipmentRepository spEquipmentRepository;

    @Autowired
    private PopulateDatabase populateDatabase;


    private EquipmentService equipmentService;

    private UUID tvId;

    @BeforeEach
    void setup() {
        initMocks(this);
        equipmentRepository.deleteAll();
        UUID sponsorshipPackageId = populateSponsorshipPackageRepository(sponsorshipPackageRepository).getId();
        List<Equipment> equipmentList = populateDatabase.populateEquipmentRepository(spEquipmentRepository, equipmentRepository, sponsorshipPackageId);
        tvId = equipmentList.get(0).getId();

        equipmentService = new EquipmentService(equipmentRepository);
    }

    @AfterEach
    void clear() {
        sponsorshipPackageRepository.deleteAll();
        spEquipmentRepository.deleteAll();
        equipmentRepository.deleteAll();
    }

    @Test
    @DisplayName("Get all equipment - success")
    void getAllEquipment_success() {

        EquipmentListResponse equipmentListResponse = equipmentService.getAllEquipment();
        assertAll(
                () -> assertEquals(2, equipmentListResponse.getCount())
        );
    }

    @Test
    @DisplayName("Get equipment by ID - success")
    void getEquipmentById_success() {

        Equipment equipment = equipmentService.getEquipmentById(tvId);
        assertAll(
                () -> assertNotNull(equipment),
                () -> assertEquals("Telewizor", equipment.getTranslations().stream().filter(translation -> translation.getLanguageCode().equals("pl")).collect(Collectors.toList()).get(0).getName())
        );
    }

    @Test
    @DisplayName("Get equipment by non existing ID - throw Exception")
    void getEquipmentByNonExistingId_throwException() {
        UUID randomId = UUID.randomUUID();
        assertThrows(NoEquipmentException.class, () -> equipmentService.getEquipmentById(randomId));
    }
}