package com.github.ser.service;

import com.github.ser.exception.badRequest.NoCompanyForUuidException;
import com.github.ser.model.database.Company;
import com.github.ser.model.database.CompanyAccess;
import com.github.ser.model.lists.CompanyListResponse;
import com.github.ser.model.requests.CreateCompanyRequest;
import com.github.ser.repository.CompanyAccessRepository;
import com.github.ser.repository.CompanyRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserService userService;

    public CompanyService(CompanyRepository companyRepository, UserService userService) {
        this.companyRepository = companyRepository;
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

    @Transactional
    public Company createNewCompany(CreateCompanyRequest createCompanyRequest) {

        Company company = Company.builder()
                .name(createCompanyRequest.getName())
                .primaryUserId(createCompanyRequest.getPrimaryUserId())
                .taxId(createCompanyRequest.getTaxId())
                .contactPhone(createCompanyRequest.getContactPhone())
                .build();

        Company savedCompany =  companyRepository.save(company);

        userService.addCompanyAccess(createCompanyRequest.getPrimaryUserId(), savedCompany.getUuid());

        return savedCompany;
    }

}
