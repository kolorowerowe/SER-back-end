package com.github.ser.service;

import com.github.ser.SerApplication;
import com.github.ser.exception.badRequest.NoCompanyForUuidException;
import com.github.ser.model.database.Company;
import com.github.ser.model.database.SponsorshipPackage;
import com.github.ser.model.database.User;
import com.github.ser.model.lists.CompanyListResponse;
import com.github.ser.model.requests.AddressRequest;
import com.github.ser.model.requests.CatalogInformationRequest;
import com.github.ser.model.requests.ChangeCompanyDetailsRequest;
import com.github.ser.model.requests.CreateCompanyRequest;
import com.github.ser.model.response.CompanyResponse;
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

import java.util.List;
import java.util.UUID;

import static com.github.ser.testutils.PopulateDatabase.populateSponsorshipPackageRepository;
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

    private CompanyService companyService;

    private UUID company1Id;
    private UUID company2Id;
    private UUID sponsorshipPackageId;
    private User user;

    @BeforeEach
    void setup() {
        initMocks(this);
        user = populateUserDatabase(userRepository).get(2);
        SponsorshipPackage sponsorshipPackage = populateSponsorshipPackageRepository(sponsorshipPackageRepository);
        List<Company> companyList = populateDatabase.populateCompanyRepository(companyRepository, sponsorshipPackage, companyAccessRepository, user);

        company1Id = companyList.get(0).getId();
        company2Id = companyList.get(1).getId();
        sponsorshipPackageId = sponsorshipPackage.getId();

        populateDatabase.populateEquipmentRepository(spEquipmentRepository, equipmentRepository, sponsorshipPackageId).get(0);

        userService = new UserService(userRepository, passwordEncoder, verificationCodeService, jwtTokenUtil, emailService);
        EquipmentService equipmentService = new EquipmentService(equipmentRepository);
        SponsorshipPackageService sponsorshipPackageService = new SponsorshipPackageService(sponsorshipPackageRepository, spEquipmentRepository, equipmentService);
        DeadlineService deadlineService = new DeadlineService(deadlineRepository);

        companyService = new CompanyService(companyRepository, companyAccessRepository, userService, sponsorshipPackageService, deadlineService);
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
    @DisplayName("Get company by id - return NoCompanyForUuidException")
    void getCompanyById_throwNoCompanyUuidException() {

        UUID notExistingCompanyUuid = UUID.randomUUID();

        assertThrows(NoCompanyForUuidException.class, () -> companyService.getCompanyById(notExistingCompanyUuid));
    }


    @Test
    @DisplayName("Get company by id - return Company")
    void getCompanyById_returnCompany() {


        Company company = companyService.getCompanyById(company1Id);

        assertAll(
                () -> assertNotNull(company),
                () -> assertEquals("Galileo", company.getName()),
                () -> assertEquals("Sponsor główny", company.getSponsorshipPackage().getTranslations().stream().findFirst().get().getName())
        );
    }

    @Test
    @DisplayName("Get company response by id - return CompanyResponse")
    void getCompanyResponseById_returnCompany() {


        CompanyResponse companyResponse = companyService.getCompanyResponseById(company1Id);

        assertAll(
                () -> assertNotNull(companyResponse),
                () -> assertEquals("Galileo", companyResponse.getName()),
                () -> assertEquals("Sponsor główny", companyResponse.getSponsorshipPackage().getTranslations().stream().findFirst().get().getName())
        );
    }

    @Test
    @DisplayName("Get all companies - return CompanyListResponse")
    void getAllCompanies_returnCompanyListResponse() {


        CompanyListResponse companyListResponse = companyService.getAllCompanies();

        assertAll(
                () -> assertNotNull(companyListResponse),
                () -> assertEquals(2, companyListResponse.getCount()),
                () -> assertEquals("Galileo", companyListResponse.getCompanyList().get(0).getName())
        );
    }

    @Test
    @DisplayName("Get all companies for user - return CompanyListResponse")
    void getAllCompaniesForUser_returnCompanyListResponse() {


        CompanyListResponse companyListResponse = companyService.getCompaniesForUser(user.getId());

        assertAll(
                () -> assertNotNull(companyListResponse),
                () -> assertEquals(2, companyListResponse.getCount()),
                () -> assertEquals("Galileo", companyListResponse.getCompanyList().get(0).getName())
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
                () -> assertTrue(userWithCompanyAccess.getCompanyAccessList().stream().anyMatch(companyAccess -> companyAccess.getCompanyId().equals(company.getId()))),
                () -> assertNull(company.getCatalogInformation()),
                () -> assertNull(company.getSponsorshipPackage())
        );

    }

    @Test
    @DisplayName("Delete company by id")
    void deleteCompanyById() {

        assertDoesNotThrow(() -> companyService.getCompanyById(company1Id));

        companyService.deleteCompanyById(company1Id);

        assertThrows(NoCompanyForUuidException.class, () -> companyService.getCompanyById(company1Id));

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

        CompanyResponse updatedCompany = companyService.changeCompanyDetails(company1Id, changeCompanyDetailsRequest);

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

        CompanyResponse updatedCompany = companyService.changeCompanyDetails(company1Id, changeCompanyDetailsRequest);

        assertAll(
                () -> assertEquals("+5678", updatedCompany.getContactPhone()),
                () -> assertEquals("8888", updatedCompany.getTaxId()),
                () -> assertEquals("12", updatedCompany.getAddress().getBuildingNumber())
        );

    }

    @Test
    @DisplayName("Change company details - Catalog information")
    void changeCompanyDetails_changeCatalogInformation() {

        ChangeCompanyDetailsRequest changeCompanyDetailsRequest = ChangeCompanyDetailsRequest.builder()
                .catalogInformationRequest(CatalogInformationRequest.builder()
                        .companyName("my galileo company")
                        .paidInternships(false)
                        .numberOfEmployeesPoland(100)
                        .build())
                .build();

        CompanyResponse updatedCompany = companyService.changeCompanyDetails(company1Id, changeCompanyDetailsRequest);

        assertAll(
                () -> assertEquals("Galileo", updatedCompany.getName()),
                () -> assertEquals("+1234", updatedCompany.getContactPhone()),
                () -> assertEquals("my galileo company", updatedCompany.getCatalogInformation().getCompanyName()),
                () -> assertEquals(false, updatedCompany.getCatalogInformation().getPaidInternships()),
                () -> assertEquals(100, updatedCompany.getCatalogInformation().getNumberOfEmployeesPoland()),
                () -> assertNull(updatedCompany.getCatalogInformation().getCandidateRequirements())
        );

    }

    @Test
    @DisplayName("Set sponsorship package - success")
    void setSponsorshipPackage_success() {

        CompanyResponse company = companyService.getCompanyResponseById(company2Id);

        assertAll(
                () -> assertNotNull(company),
                () -> assertNull(company.getSponsorshipPackage())
        );

        CompanyResponse updatedCompany = companyService.setSponsorshipPackage(company2Id, sponsorshipPackageId);

        assertAll(
                () -> assertNotNull(updatedCompany),
                () -> assertNotNull(updatedCompany.getSponsorshipPackage()),
                () -> assertEquals(sponsorshipPackageId, updatedCompany.getSponsorshipPackage().getId())
        );

    }

    @Test
    @DisplayName("Export company to csv")
    void exportCompanyToCsv() {

        String companiesCsv = companyService.exportCompaniesToCsv();

        assertAll(
                () -> assertNotEquals(0, companiesCsv.length()),
                () -> assertTrue(companiesCsv.contains("Company Name")),
                () -> assertTrue(companiesCsv.contains("Galileo")),
                () -> assertTrue(companiesCsv.contains("Copernicus"))
        );

    }

    @Test
    @DisplayName("Export catalog info to csv")
    void exportCatalogInformationToCsv() {

        String catalogCsv = companyService.exportCatalogInformationToCsv();

        assertAll(
                () -> assertNotEquals(0, catalogCsv.length()),
                () -> assertTrue(catalogCsv.contains("Description"))
        );

    }
}