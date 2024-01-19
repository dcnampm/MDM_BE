package com.mdm.equipmentservice.model.dto.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePasswordForm {
    @NotBlank(message = "{oldPasswordMustNotBeBlank}")
    private String oldPassword;
    @NotBlank(message = "{newPasswordMustNotBeBlank}")
    private String newPassword;
    @NotBlank(message = "{confirmPasswordMustNotBeBlank}")
    private String confirmPassword;
}
