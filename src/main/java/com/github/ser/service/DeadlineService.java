package com.github.ser.service;

import com.github.ser.exception.badRequest.DeadlinesNotInitializedException;
import com.github.ser.model.database.Deadline;
import com.github.ser.model.lists.DeadlineListResponse;
import com.github.ser.repository.DeadlineRepository;
import com.github.ser.util.DeadlineUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Log4j2
public class DeadlineService {

    private final DeadlineRepository deadlineRepository;

    public DeadlineService(DeadlineRepository deadlineRepository) {
        this.deadlineRepository = deadlineRepository;
    }

    public DeadlineListResponse getAllDeadlines() {
        List<Deadline> allDeadlines = deadlineRepository.findAll();
        if (allDeadlines.size() != 5) {
            throw new DeadlinesNotInitializedException("Deadline list size: " + allDeadlines.size() + ", assuming than deadlines are not initialized");
        }

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
}
