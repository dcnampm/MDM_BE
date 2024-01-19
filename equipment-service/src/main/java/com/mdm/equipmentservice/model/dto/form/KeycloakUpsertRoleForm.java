package com.mdm.equipmentservice.model.dto.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * For updating and inserting role only
 */
@Data
public class KeycloakUpsertRoleForm implements Serializable {
    @NotBlank(message = "{roleNameMustNotBeNull}")
    private String name;

    private String description;

    private Set<String> scopes;
}
