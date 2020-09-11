package com.github.ser.service;

import com.github.ser.exception.badRequest.NoCompanyForUuidException;
import com.github.ser.model.database.*;
import com.github.ser.model.lists.CompanyDeadlineStatusesDTO;
import com.github.ser.model.lists.CompanyListResponse;
import com.github.ser.model.requests.ChangeCompanyDetailsRequest;
import com.github.ser.model.requests.CreateCompanyRequest;
import com.github.ser.model.response.CompanyResponse;
import com.github.ser.repository.CompanyAccessRepository;
import com.github.ser.repository.CompanyRepository;
import com.github.ser.util.ModelUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyAccessRepository companyAccessRepository;
    private final UserService userService;
    private final SponsorshipPackageService sponsorshipPackageService;
    private final DeadlineService deadlineService;

    public CompanyService(CompanyRepository companyRepository, CompanyAccessRepository companyAccessRepository, UserService userService, SponsorshipPackageService sponsorshipPackageService, DeadlineService deadlineService) {
        this.companyRepository = companyRepository;
        this.companyAccessRepository = companyAccessRepository;
        this.userService = userService;
        this.sponsorshipPackageService = sponsorshipPackageService;
        this.deadlineService = deadlineService;
    }

    public CompanyListResponse getAllCompanies() {
        List<Company> companies = companyRepository.findAll();

        List<CompanyResponse> companyResponseList = companies.stream()
                .map(this::getCompanyResponse)
                .collect(Collectors.toList());

        return CompanyListResponse.builder()
                .companyList(companyResponseList)
                .count(companyResponseList.size())
                .build();

    }

    public Company getCompanyById(UUID companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company == null) {
            log.debug("Company: " + companyId + " does not exist");
            throw new NoCompanyForUuidException("No user for provided email");
        }
        return company;
    }

    public CompanyResponse getCompanyResponseById(UUID companyId) {
        Company company = getCompanyById(companyId);
        return getCompanyResponse(company);
    }

    private CompanyResponse getCompanyResponse(Company company) {
        User primaryUser = userService.getUserById(company.getPrimaryUserId());
        CompanyDeadlineStatusesDTO companyDeadlineStatuses = deadlineService.getDeadlineStatusForCompany(company);

        return new CompanyResponse(
                company,
                primaryUser,
                companyDeadlineStatuses
        );
    }

    public CompanyListResponse getCompaniesForUser(UUID userId) {

        List<CompanyAccess> companyAccesses = userService.getUserById(userId).getCompanyAccessList();
        List<CompanyResponse> companies = companyAccesses
                .stream()
                .map(companyAccess -> getCompanyResponseById(companyAccess.getCompanyId()))
                .collect(Collectors.toList());

        return CompanyListResponse.builder()
                .companyList(companies)
                .count(companies.size())
                .build();
    }

    @Transactional
    public CompanyResponse createNewCompany(CreateCompanyRequest createCompanyRequest) {

        Company company = Company.builder()
                .name(createCompanyRequest.getName())
                .primaryUserId(createCompanyRequest.getPrimaryUserId())
                .taxId(createCompanyRequest.getTaxId())
                .contactPhone(createCompanyRequest.getContactPhone())
                .companyCreatedDate(LocalDateTime.now())
                .build();

        Address companyAddress = Address.builder()
                .street(createCompanyRequest.getAddress().getStreet())
                .buildingNumber(createCompanyRequest.getAddress().getBuildingNumber())
                .flatNumber(createCompanyRequest.getAddress().getFlatNumber())
                .city(createCompanyRequest.getAddress().getCity())
                .postalCode(createCompanyRequest.getAddress().getPostalCode())
                .company(company)
                .build();

        company.setAddress(companyAddress);

        Company savedCompany = companyRepository.save(company);

        userService.addCompanyAccess(
                createCompanyRequest.getPrimaryUserId(),
                savedCompany.getId(),
                savedCompany.getName()
        );

        return getCompanyResponse(savedCompany);
    }

    @Transactional
    public void deleteCompanyById(UUID companyId) {
        companyAccessRepository.deleteAllByCompanyId(companyId);
        companyRepository.deleteById(companyId);
    }

    public CompanyResponse changeCompanyDetails(UUID companyId, ChangeCompanyDetailsRequest changeCompanyDetailsRequest) {
        Company company = getCompanyById(companyId);

        Company updatedCompany = ModelUtils.copyCompanyNonNullProperties(company, changeCompanyDetailsRequest);

        Company savedCompany = companyRepository.save(updatedCompany);
        return getCompanyResponse(savedCompany);
    }

    public CompanyResponse setSponsorshipPackage(UUID companyId, UUID sponsorshipPackageId) {
        Company company = getCompanyById(companyId);
        SponsorshipPackage sponsorshipPackage = sponsorshipPackageService.getSponsorshipPackageById(sponsorshipPackageId);

        company.setSponsorshipPackage(sponsorshipPackage);

        Company savedCompany = companyRepository.save(company);
        return getCompanyResponse(savedCompany);
    }

}
