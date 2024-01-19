package com.mdm.equipmentservice.model.dto.form;


import com.mdm.equipmentservice.model.entity.User;
import com.mdm.equipmentservice.model.entity.WorkingStatus;
import com.querydsl.core.BooleanBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * A DTO for the {@link User} entity Use for updating + inserting User
 */
@Data
public class UpsertUserForm implements Serializable {

    @Schema(hidden = true)
    private Long id;

    @NotBlank(message = "{nameMustNotBeBlank}")
    private String name;

    @NotBlank(message = "{usernameMustNotBeBlank}")
    private String username;

    @NotBlank(message = "{emailMustNotBeBlank}")
    @Email(message = "{wrongEmailFormat}")
    private String email;

    @NotBlank(message = "{phoneMustNotBeBlank}")
    private String phone;

    @NotNull(message = "{genderMustNotBeNull}")
    private Boolean gender;

    private String address;

    @NotNull(message = "{birthdayMustNotBeNull}")
    private LocalDateTime birthday;

    private boolean enabled;

    private WorkingStatus workingStatus;

    @NotNull(message = "{roleMustNotBeBlank}")
    private Long roleId;

    @NotNull(message = "{departmentMustNotBeNull}")
    private Long departmentId;

    private List<Long> departmentResponsibilityIds;

    private String password;
}