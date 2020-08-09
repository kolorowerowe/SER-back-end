package com.github.ser.service;

import com.github.ser.exception.badRequest.NoCompanyForUuidException;
import com.github.ser.model.database.Address;
import com.github.ser.model.database.Company;
import com.github.ser.model.database.CompanyAccess;
import com.github.ser.model.lists.CompanyListResponse;
import com.github.ser.model.requests.ChangeCompanyDetailsRequest;
import com.github.ser.model.requests.CreateCompanyRequest;
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

    public CompanyService(CompanyRepository companyRepository, CompanyAccessRepository companyAccessRepository, UserService userService) {
        this.companyRepository = companyRepository;
        this.companyAccessRepository = companyAccessRepository;
        this.userService = userService;
    }

    public CompanyListResponse getAllCompanies() {
        List<Company> companies = companyRepository.findAll();

        return CompanyListResponse.builder()
                .companyList(companies)
                .count(companies.size())
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

    public CompanyListResponse getCompaniesForUser(UUID userId) {

        List<CompanyAccess> companyAccesses = userService.getUserById(userId).getCompanyAccessList();
        List<Company> companies = companyAccesses
                .stream()
                .map(companyAccess -> getCompanyById(companyAccess.getCompanyId()))
                .collect(Collectors.toList());

        return CompanyListResponse.builder()
                .companyList(companies)
                .count(companies.size())
                .build();
    }

    @Transactional
    public Company createNewCompany(CreateCompanyRequest createCompanyRequest) {

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

        return savedCompany;
    }

    @Transactional
    public void deleteCompanyById(UUID companyId) {
        companyAccessRepository.deleteAllByCompanyId(companyId);
        companyRepository.deleteById(companyId);
    }

    public Company changeCompanyDetails(UUID companyId, ChangeCompanyDetailsRequest changeCompanyDetailsRequest) {
        Company company = getCompanyById(companyId);

        Company updatedCompany = ModelUtils.copyCompanyNonNullProperties(company, changeCompanyDetailsRequest);

        return companyRepository.save(updatedCompany);
    }

}
