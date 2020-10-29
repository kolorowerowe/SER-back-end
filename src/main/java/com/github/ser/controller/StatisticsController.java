package com.github.ser.controller;

import com.github.ser.model.statistics.Statistics;
import com.github.ser.service.StatisticsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@Log4j2
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public ResponseEntity<Statistics> getStatistics() {
        log.info("Getting all statistics");
        return new ResponseEntity<>(statisticsService.getStatistics(), HttpStatus.OK);
    }

}
