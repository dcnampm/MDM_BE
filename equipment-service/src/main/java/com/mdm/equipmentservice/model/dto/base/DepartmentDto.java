package com.mdm.equipmentservice.model.dto.base;

import com.mdm.equipmentservice.model.entity.Department;
import com.mdm.equipmentservice.model.entity.DepartmentActiveStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Department} entity
 */
@Data
public class DepartmentDto implements Serializable {
    private final Long id;

    private final String name;

    private final String alias;

    private final String phone;

    private final String email;

    private final String address;

    private final DepartmentActiveStatus activeStatus;
}