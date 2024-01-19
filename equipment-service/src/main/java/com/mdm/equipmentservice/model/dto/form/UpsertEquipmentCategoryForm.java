package com.mdm.equipmentservice.model.dto.form;

import com.mdm.equipmentservice.model.entity.EquipmentCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link EquipmentCategory} entity
 */
@Data
public class UpsertEquipmentCategoryForm implements Serializable {
    @NotBlank(message = "{equipmentCategoryNameMustNotBeBlank}")
    private final String name;

    private final String note;

    private final String alias;

    private final Long groupId;
}