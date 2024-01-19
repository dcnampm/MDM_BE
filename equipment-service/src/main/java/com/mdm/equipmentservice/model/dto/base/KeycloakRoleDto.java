package com.mdm.equipmentservice.model.dto.base;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class KeycloakRoleDto implements Serializable {
    private String name;

    private String description;

    private List<String> scopes;
}