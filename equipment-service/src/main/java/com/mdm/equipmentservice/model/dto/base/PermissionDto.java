package com.mdm.equipmentservice.model.dto.base;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.Permission}
 */
@Value
public class PermissionDto implements Serializable {

    Long id;

    String name;

    String description;
}