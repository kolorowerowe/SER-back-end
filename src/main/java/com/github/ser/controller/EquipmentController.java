package com.github.ser.controller;

import com.github.ser.model.database.Equipment;
import com.github.ser.model.lists.EquipmentListResponse;
import com.github.ser.model.requests.CreateEquipmentRequest;
import com.github.ser.service.EquipmentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/equipment")
@Log4j2
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public ResponseEntity<EquipmentListResponse> getAllEquipments() {
        log.info("Getting all equipments");
        return new ResponseEntity<>(equipmentService.getAllEquipment(), HttpStatus.OK);
    }

    @GetMapping("/{equipmentId}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable UUID equipmentId) {
        log.info("Getting sponsorship package by id: " + equipmentId);
        return new ResponseEntity<>(equipmentService.getEquipmentById(equipmentId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Equipment> addEquipment(@RequestBody CreateEquipmentRequest createEquipmentRequest) {
        log.info("Adding new sponsorship package");
        return new ResponseEntity<>(equipmentService.addEquipment(createEquipmentRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{equipmentId}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable UUID equipmentId) {
        log.info("Deleting equipment: " + equipmentId);
        equipmentService.deleteEquipment(equipmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{equipmentId}")
    public ResponseEntity<Equipment> changeEquipment(@PathVariable UUID equipmentId, @RequestBody CreateEquipmentRequest changeEquipmentRequest) {
        log.info("Changing equipment details: " + equipmentId);
        return new ResponseEntity<>(equipmentService.changeEquipmentDetails(equipmentId, changeEquipmentRequest), HttpStatus.OK);
    }

}
