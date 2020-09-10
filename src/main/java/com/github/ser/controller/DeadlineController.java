package com.github.ser.controller;

import com.github.ser.model.lists.DeadlineListResponse;
import com.github.ser.service.DeadlineService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deadline")
@Log4j2
public class DeadlineController {

    private final DeadlineService deadlineService;

    public DeadlineController(DeadlineService deadlineService) {
        this.deadlineService = deadlineService;
    }

    @GetMapping()
    public ResponseEntity<DeadlineListResponse> getAllDeadlines() {
        log.info("Getting all deadlines");
        return new ResponseEntity<>(deadlineService.getAllDeadlines(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<DeadlineListResponse> setAllDeadlines(@RequestBody DeadlineListResponse deadlineListResponse) {
        log.info("Setting all deadlines");
        return new ResponseEntity<>(deadlineService.setAllDeadlines(deadlineListResponse), HttpStatus.OK);
    }


}
