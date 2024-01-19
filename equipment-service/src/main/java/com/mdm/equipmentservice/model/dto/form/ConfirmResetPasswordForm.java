package com.mdm.equipmentservice.model.dto.form;

import lombok.Data;

@Data
public class ConfirmResetPasswordForm {
    private String newPassword;
    private String confirmPassword;
    private String uuid;
}
