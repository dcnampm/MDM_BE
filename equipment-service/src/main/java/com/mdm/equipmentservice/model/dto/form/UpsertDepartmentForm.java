package com.mdm.equipmentservice.model.dto.form;

import com.mdm.equipmentservice.model.entity.Department;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Department} entity
 */
@Data
public class UpsertDepartmentForm implements Serializable {
    @NotBlank(message = "{departmentNameMustNotBeBlank}")
    private final String name;

    private final String alias;

    private final String phone;

    private final String email;

    private final String address;

    private final Long contactPersonId;

    private final Long headOfDepartmentId;

    private final Long chiefNurseId;

    private final Long managerId;
}