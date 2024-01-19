package com.mdm.equipmentservice.model.dto.base;

import com.mdm.equipmentservice.model.entity.EquipmentCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link EquipmentCategory} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentCategoryDto implements Serializable {
    private Long id;

    private String name;

    private String note;

    private String alias;

    private EquipmentGroupDto group;
}