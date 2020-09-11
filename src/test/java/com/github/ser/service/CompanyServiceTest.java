package com.github.ser.service;

import com.github.ser.SerApplication;
import com.github.ser.exception.badRequest.NoCompanyForUuidException;
import com.github.ser.model.database.Company;
import com.github.ser.model.database.User;
import com.github.ser.model.lists.CompanyListResponse;
import com.github.ser.model.requests.AddressRequest;
import com.github.ser.model.requests.ChangeCompanyDetailsRequest;
import com.github.ser.model.requests.CreateCompanyRequest;
import com.github.ser.model.response.CompanyResponse;
import com.github.ser.repository.*;
import com.github.ser.util.JwtTokenUtil;
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

import static com.github.ser.testutils.PopulateDatabase.populateCompanyRepository;
import static com.github.ser.testutils.PopulateDatabase.populateUserDatabase;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Company Service tests")
class CompanyServiceTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyAccessRepository companyAccessRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Autowired
    private SponsorshipPackageRepository sponsorshipPackageRepository;

    @Mock
    private SponsorshipPackageService sponsorshipPackageService;

    @Autowired
    private DeadlineRepository deadlineRepository;

    @Mock
    private DeadlineService deadlineService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mock
    private VerificationCodeService verificationCodeService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private EmailService emailService;

    private CompanyService companyService;

    private UUID companyUuid;
    private User user;

    @BeforeEach
    void setup() {
        initMocks(this);

        user = populateUserDatabase(userRepository).get(2);
        companyUuid = populateCompanyRepository(companyRepository, sponsorshipPackageRepository, user).get(0);

        userService = new UserService(userRepository, passwordEncoder, verificationCodeService, jwtTokenUtil, emailService);
        sponsorshipPackageService = new SponsorshipPackageService(sponsorshipPackageRepository);
        deadlineService = new DeadlineService(deadlineRepository);

        companyService = new CompanyService(companyRepository, companyAccessRepository, userService, sponsorshipPackageService, deadlineService);
    }

    @Test
    @DisplayName("Get company by id - return NoCompanyForUuidException")
    void getCompanyById_throwNoCompanyUuidException() {

        UUID notExistingCompanyUuid = UUID.fromString("00000000-9999-9999-9999-000000000000");

        assertThrows(NoCompanyForUuidException.class, () -> companyService.getCompanyById(notExistingCompanyUuid));
    }


    @Test
    @DisplayName("Get company by id - return Company")
    void getCompanyById_returnCompany() {


        Company company = companyService.getCompanyById(companyUuid);

        assertAll(
                () -> assertNotNull(company),
                () -> assertEquals("Galileo", company.getName()),
                () -> assertEquals("Sponsor główny", company.getSponsorshipPackage().getTranslations().stream().findFirst().get().getName())
        );
    }

    @Test
    @DisplayName("Get all companies - return CompanyListResponse")
    void getAllCompanies_returnCompanyListResponse() {


        CompanyListResponse companyListResponse = companyService.getAllCompanies();

        assertAll(
                () -> assertNotNull(companyListResponse),
                () -> assertEquals(2, companyListResponse.getCount()),
                () -> assertEquals("Galileo", companyListResponse.getCompanyList().get(0))
        );
    }

    @Test
    @DisplayName("Create new company - add new company to db")
    void createNewCompany_addNewCompany() {

        CreateCompanyRequest createCompanyRequest = CreateCompanyRequest.builder()
                .name("Zeus")
                .contactPhone("+1234")
                .taxId("111")
                .primaryUserId(user.getId())
                .address(AddressRequest.builder()
                        .buildingNumber("12")
                        .city("Kraków")
                        .postalCode("12-12")
                        .street("aa")
                        .build())
                .build();

        CompanyResponse company = companyService.createNewCompany(createCompanyRequest);
        User userWithCompanyAccess = userService.getUserById(user.getId());

        assertAll(
                () -> assertEquals("Zeus", company.getName()),
                () -> assertEquals(user.getId(), company.getPrimaryUser().getId()),
                () -> assertEquals("Kraków", company.getAddress().getCity()),
                () -> assertTrue(userWithCompanyAccess.getCompanyAccessList().stream().anyMatch(companyAccess -> companyAccess.getCompanyId().equals(company.getId())))
        );

    }

    @Test
    @DisplayName("Delete company by id")
    void deleteCompanyById() {

        assertDoesNotThrow(() -> companyService.getCompanyById(companyUuid));

        companyService.deleteCompanyById(companyUuid);

        assertThrows(NoCompanyForUuidException.class, () -> companyService.getCompanyById(companyUuid));

    }

    @Test
    @DisplayName("Change company details - change only Contact phone")
    void changeCompanyDetails_changePhone() {

        ChangeCompanyDetailsRequest changeCompanyDetailsRequest = ChangeCompanyDetailsRequest.builder()
                .contactPhone("+5678")
                .taxId("")
                .address(AddressRequest.builder()
                        .buildingNumber("12")
                        .build())
                .build();

        CompanyResponse updatedCompany = companyService.changeCompanyDetails(companyUuid, changeCompanyDetailsRequest);

        assertAll(
                () -> assertEquals("+5678", updatedCompany.getContactPhone()),
                () -> assertEquals("999", updatedCompany.getTaxId())
        );

    }

    @Test
    @DisplayName("Change company details - Contact phone and Tax ID")
    void changeCompanyDetails_changePhoneAndTaxId() {

        ChangeCompanyDetailsRequest changeCompanyDetailsRequest = ChangeCompanyDetailsRequest.builder()
                .contactPhone("+5678")
                .taxId("8888")
                .address(AddressRequest.builder()
                        .buildingNumber("12")
                        .build())
                .build();

        CompanyResponse updatedCompany = companyService.changeCompanyDetails(companyUuid, changeCompanyDetailsRequest);

        assertAll(
                () -> assertEquals("+5678", updatedCompany.getContactPhone()),
                () -> assertEquals("8888", updatedCompany.getTaxId())
        );

    }

}