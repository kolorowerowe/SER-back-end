package com.github.ser.service;

import com.github.ser.model.database.Company;
import com.github.ser.model.database.Deadline;
import com.github.ser.model.dto.CompanyDeadlineStatusDTO;
import com.github.ser.model.lists.CompanyDeadlineStatusesDTO;
import com.github.ser.model.lists.DeadlineListResponse;
import com.github.ser.repository.DeadlineRepository;
import com.github.ser.util.DeadlineUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.ser.model.dto.CompanyDeadlineStatusDTO.getCompanyDeadlineStatusDTO;

@Service
@Log4j2
public class DeadlineService {

    private final DeadlineRepository deadlineRepository;

    public DeadlineService(DeadlineRepository deadlineRepository) {
        this.deadlineRepository = deadlineRepository;
    }

    public DeadlineListResponse getAllDeadlines() {
        List<Deadline> allDeadlines = deadlineRepository.findAll();

        allDeadlines.sort(Comparator.comparing(Deadline::getOrderNumber));

        return DeadlineListResponse.builder()
                .deadlines(allDeadlines)
                .count(allDeadlines.size())
                .build();
    }

    public DeadlineListResponse setAllDeadlines(DeadlineListResponse newDeadlines) {

        DeadlineUtils.validateDeadlines(newDeadlines.getDeadlines());

        deadlineRepository.deleteAll();
        List<Deadline> savedDeadlines = deadlineRepository.saveAll(newDeadlines.getDeadlines());

        return DeadlineListResponse.builder()
                .deadlines(savedDeadlines)
                .count(savedDeadlines.size())
                .build();
    }

    public CompanyDeadlineStatusesDTO getDeadlineStatusForCompany(Company company) {
        List<Deadline> deadlines = getAllDeadlines().getDeadlines();

        List<CompanyDeadlineStatusDTO> companyDeadlineStatusList = deadlines.stream()
                .map(deadline -> getCompanyDeadlineStatusDTO(deadline, company)).collect(Collectors.toList());

        return CompanyDeadlineStatusesDTO.builder()
                .companyDeadlineStatuses(companyDeadlineStatusList)
                .count(companyDeadlineStatusList.size())
                .build();
    }

}
