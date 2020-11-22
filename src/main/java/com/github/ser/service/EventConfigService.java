package com.github.ser.service;

import com.github.ser.exception.badRequest.EventConfigInvalidException;
import com.github.ser.exception.badRequest.EventConfigNotInitializedException;
import com.github.ser.model.database.Company;
import com.github.ser.model.database.Deadline;
import com.github.ser.model.database.EventConfig;
import com.github.ser.model.dto.CompanyDeadlineStatusDTO;
import com.github.ser.model.lists.CompanyDeadlineStatusesDTO;
import com.github.ser.model.lists.DeadlineListResponse;
import com.github.ser.repository.DeadlineRepository;
import com.github.ser.repository.EventConfigRepository;
import com.github.ser.util.DeadlineUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.ser.model.dto.CompanyDeadlineStatusDTO.getCompanyDeadlineStatusDTO;

@Service
@Log4j2
public class EventConfigService {

    private final EventConfigRepository eventConfigRepository;

    public EventConfigService(EventConfigRepository eventConfigRepository) {
        this.eventConfigRepository = eventConfigRepository;
    }

    public EventConfig getEventConfig() {

        List<EventConfig> eventConfigList = eventConfigRepository.findAll();
        if (eventConfigList.isEmpty()){
            throw new EventConfigNotInitializedException("Event config not initialized");
        }

        return eventConfigList.get(0);
    }

    public EventConfig updateEventConfig(EventConfig eventConfig) {

        if (eventConfig.getEventDate() == null){
            throw new EventConfigInvalidException("Event date is null");
        }

        eventConfigRepository.deleteAll();
        return eventConfigRepository.save(eventConfig);
    }
}
