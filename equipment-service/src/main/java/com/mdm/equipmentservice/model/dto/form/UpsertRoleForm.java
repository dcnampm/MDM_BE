package com.mdm.equipmentservice.model.dto.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.Role}
 */
@Value
public class UpsertRoleForm implements Serializable {

    @NotBlank
    String name;

    String description;

    Set<String> permissions;
}