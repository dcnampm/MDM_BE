package com.mdm.equipmentservice.model.dto.fullinfo;

import com.mdm.equipmentservice.model.dto.base.EquipmentCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.EquipmentGroup}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentGroupFullInfoDto implements Serializable {
    private Long id;

    private String name;

    private String note;

    private String alias;

    private List<EquipmentCategoryDto> equipmentCategories;
}