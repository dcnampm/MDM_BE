package com.mdm.equipmentservice.model.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.EquipmentUnit}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentUnitDto implements Serializable {
    private Long id;

    private String name;

    private String note;
}