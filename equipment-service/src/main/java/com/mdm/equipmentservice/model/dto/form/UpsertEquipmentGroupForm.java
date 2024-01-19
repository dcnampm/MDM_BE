package com.mdm.equipmentservice.model.dto.form;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.EquipmentGroup}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertEquipmentGroupForm implements Serializable {
    @NotBlank(message = "{equipmentGroupNameMustNotBeBlank}")
    private String name;

    private String note;

    @NotBlank(message = "{aliasMustNotBeBlank}")
    private String alias;
}