package com.github.ser.controller;

import com.github.ser.model.database.EventConfig;
import com.github.ser.model.lists.DeadlineListResponse;
import com.github.ser.service.DeadlineService;
import com.github.ser.service.EventConfigService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event-config")
@Log4j2
public class EventConfigController {

    private final EventConfigService eventConfigService;

    public EventConfigController(EventConfigService eventConfigService) {
        this.eventConfigService = eventConfigService;
    }

    @GetMapping
    public ResponseEntity<EventConfig> getEventConfig() {
        log.info("Getting event config");
        return new ResponseEntity<>(eventConfigService.getEventConfig(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EventConfig> updateEventConfig(@RequestBody EventConfig eventConfig) {
        log.info("Saving event config");
        return new ResponseEntity<>(eventConfigService.updateEventConfig(eventConfig), HttpStatus.OK);
    }

}
