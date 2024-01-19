package com.mdm.equipmentservice.model.dto.fullinfo;

import com.mdm.equipmentservice.model.entity.Department;
import com.mdm.equipmentservice.model.entity.DepartmentActiveStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link Department} entity
 */
@Data
public class DepartmentFullInfoDto implements Serializable {
    private final Long id;

    private final String name;

    private final String alias;

    private final String phone;

    private final String email;

    private final String address;

    private final DepartmentActiveStatus activeStatus;

    private final UserDetailDto contactPerson;

    private final UserDetailDto headOfDepartment;

    private final UserDetailDto chiefNurse;

    private final UserDetailDto manager;
    private final Set<UserDetailDto> users;

}