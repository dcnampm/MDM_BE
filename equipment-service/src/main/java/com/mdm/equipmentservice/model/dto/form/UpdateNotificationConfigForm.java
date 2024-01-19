package com.mdm.equipmentservice.model.dto.form;

import com.mdm.equipmentservice.model.entity.NotificationType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNotificationConfigForm {

    @NotNull
    private NotificationType notificationType;
    @NotNull
    private Long roleId;
}
