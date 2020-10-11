package com.github.ser.service;

import com.github.ser.exception.badRequest.NoEquipmentException;
import com.github.ser.model.database.Equipment;
import com.github.ser.model.lists.EquipmentListResponse;
import com.github.ser.model.requests.CreateEquipmentRequest;
import com.github.ser.repository.EquipmentRepository;
import com.github.ser.util.ModelUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;


    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public Equipment getEquipmentById(UUID equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);
        if (equipment == null) {
            log.debug("Equipment: " + equipmentId + " does not exist");
            throw new NoEquipmentException("No equipment found: " + equipmentId);
        }
        return equipment;
    }

    public EquipmentListResponse getAllEquipment() {
        List<Equipment> equipmentList = equipmentRepository.findAll();

        return EquipmentListResponse.builder()
                .equipmentList(equipmentList)
                .count(equipmentList.size())
                .build();
    }

    public Equipment addEquipment(CreateEquipmentRequest createEquipmentRequest) {
        Equipment newSponsorshipPackage = Equipment.builder()
                .translations(createEquipmentRequest.getTranslations())
                .prices(createEquipmentRequest.getPrices())
                .maxCountPerCompany(createEquipmentRequest.getMaxCountPerCompany())
                .build();

        return equipmentRepository.save(newSponsorshipPackage);
    }

    public Equipment changeEquipmentDetails(UUID equipmentId, CreateEquipmentRequest createEquipmentRequest) {
        Equipment equipment = getEquipmentById(equipmentId);

        Equipment updatedEquipment = ModelUtils.copyEquipmentNonNullProperties(equipment, createEquipmentRequest);

        return equipmentRepository.save(updatedEquipment);
    }

    public void deleteEquipment(UUID equipmentId) {
        equipmentRepository.deleteById(equipmentId);
    }


}
