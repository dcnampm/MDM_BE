package com.mdm.equipmentservice.model.dto.fullinfo;

import com.mdm.equipmentservice.model.entity.DepartmentActiveStatus;
import com.mdm.equipmentservice.model.entity.User;
import com.mdm.equipmentservice.model.entity.WorkingStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * A DTO for the {@link User} entity
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserFullInfoDto extends RepresentationModel<UserFullInfoDto> implements Serializable {
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

    private String roleName;

    private DepartmentDto department;

    private List<DepartmentDto> departmentResponsibilities;

    /**
     * A DTO for the {@link com.mdm.equipmentservice.model.entity.Department} entity
     */
    @Data
    public static class DepartmentDto implements Serializable {
        private Long id;

        private String name;

        private String alias;

        private String phone;

        private String email;

        private String address;

        private DepartmentActiveStatus activeStatus;
    }

}