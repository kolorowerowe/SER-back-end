package com.github.ser.model.lists;

import com.github.ser.model.database.Equipment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class EquipmentListResponse {
    private List<Equipment> equipmentList;
    private int count;
}
