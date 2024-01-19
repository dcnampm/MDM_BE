package com.mdm.equipmentservice.model.dto.fullinfo;

import com.mdm.equipmentservice.model.dto.base.PermissionDto;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.Role}
 */
@Value
public class RoleFullInfoDto implements Serializable {

    Long id;

    String name;

    String description;

    Set<PermissionDto> permissions;
}