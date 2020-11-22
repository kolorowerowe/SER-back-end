package com.github.ser.service;

import com.github.ser.SerApplication;
import com.github.ser.model.database.SponsorshipPackage;
import com.github.ser.model.database.User;
import com.github.ser.model.statistics.CompanyStatistics;
import com.github.ser.model.statistics.SponsorshipPackageStatistics;
import com.github.ser.model.statistics.UserStatistics;
import com.github.ser.repository.*;
import com.github.ser.testutils.PopulateDatabase;
import com.github.ser.util.JwtTokenUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static com.github.ser.testutils.PopulateDatabase.populateSponsorshipPackageRepository;
import static com.github.ser.testutils.PopulateDatabase.populateUserDatabase;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Statistics service tests")
class StatisticsServiceTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyAccessRepository companyAccessRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SponsorshipPackageRepository sponsorshipPackageRepository;

    @Autowired
    private DeadlineRepository deadlineRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private SPEquipmentRepository spEquipmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PopulateDatabase populateDatabase;

    @Mock
    private VerificationCodeService verificationCodeService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private EmailService emailService;

    private StatisticsService statisticsService;

    @BeforeEach
    void setup() {
        initMocks(this);

        User user = populateUserDatabase(userRepository).get(2);
        SponsorshipPackage sponsorshipPackage = populateSponsorshipPackageRepository(sponsorshipPackageRepository);
        populateDatabase.populateCompanyRepository(companyRepository, sponsorshipPackage, companyAccessRepository, user);
        UUID sponsorshipPackageId = sponsorshipPackage.getId();

        populateDatabase.populateEquipmentRepository(spEquipmentRepository, equipmentRepository, sponsorshipPackageId);

        UserService userService = new UserService(userRepository, passwordEncoder, verificationCodeService, jwtTokenUtil, emailService);
        EquipmentService equipmentService = new EquipmentService(equipmentRepository);
        SponsorshipPackageService sponsorshipPackageService = new SponsorshipPackageService(sponsorshipPackageRepository, spEquipmentRepository, equipmentService);
        DeadlineService deadlineService = new DeadlineService(deadlineRepository);
        CompanyService companyService = new CompanyService(companyRepository, companyAccessRepository, userService, sponsorshipPackageService, deadlineService);

        statisticsService = new StatisticsService(companyService, equipmentService, sponsorshipPackageService, userService);

    }

    @AfterEach
    void clear() {
        companyRepository.deleteAll();
        userRepository.deleteAll();
        companyAccessRepository.deleteAll();
        sponsorshipPackageRepository.deleteAll();

        spEquipmentRepository.deleteAll();
        equipmentRepository.deleteAll();
    }


    @Test
    @DisplayName("Get user statistics - success")
    void getUserStatistics_success() {

        UserStatistics userStatistics = statisticsService.getUserStatistics();

        assertAll(
                () -> assertNotNull(userStatistics),
                () -> assertEquals(3, userStatistics.getAllUsersCount())
        );

    }


    @Test
    @DisplayName("Get company statistics - success")
    void getCompanyStatistics_success() {

        CompanyStatistics companyStatistics = statisticsService.getCompanyStatistics();

        assertAll(
                () -> assertNotNull(companyStatistics),
                () -> assertEquals(2, companyStatistics.getAllCompaniesCount())
        );

    }

    @Test
    @DisplayName("Get sponsorship package statistics - success")
    void getSponsorshipPackageStatistics_success() {

        SponsorshipPackageStatistics sponsorshipPackageStatistics = statisticsService.getSponsorshipPackageStatistics();

        assertAll(
                () -> assertNotNull(sponsorshipPackageStatistics),
                () -> assertEquals(1, sponsorshipPackageStatistics.getAllSPCount()),
                () -> assertEquals(1, sponsorshipPackageStatistics.getPercentageProgressesSP().get(0).getCurrentProgress()),
                () -> assertEquals(2, sponsorshipPackageStatistics.getPercentageProgressesSP().get(0).getMaxProgress())
        );

    }
}