package com.mdm.equipmentservice.model.dto.list;

import com.mdm.equipmentservice.model.dto.base.EquipmentGroupDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.EquipmentCategory}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentCategoryListDto implements Serializable {
    private Long id;

    private String name;

    private String note;

    private String alias;

    private EquipmentGroupDto group;
}