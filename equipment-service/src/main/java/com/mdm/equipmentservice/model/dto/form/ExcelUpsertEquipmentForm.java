package com.mdm.equipmentservice.model.dto.form;

import com.mdm.equipmentservice.model.entity.EquipmentStatus;
import lombok.Data;

@Data
public class ExcelUpsertEquipmentForm {
    private EquipmentStatus status;
    private Long departmentId;

}
