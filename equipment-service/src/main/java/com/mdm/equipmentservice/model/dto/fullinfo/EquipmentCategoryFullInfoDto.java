package com.mdm.equipmentservice.model.dto.fullinfo;

import com.mdm.equipmentservice.model.dto.base.EquipmentGroupDto;
import com.mdm.equipmentservice.model.entity.EquipmentCategory;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link EquipmentCategory} entity
 */
@Data
public class EquipmentCategoryFullInfoDto implements Serializable {

    private Long id;

    private String name;

    private String note;

    private String alias;

    private EquipmentGroupDto group;
}