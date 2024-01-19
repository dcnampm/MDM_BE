package com.mdm.equipmentservice.model.dto.fullinfo;

import com.mdm.equipmentservice.model.dto.base.RoleDto;
import com.mdm.equipmentservice.model.entity.NotificationType;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.mdm.equipmentservice.model.entity.NotificationConfig}
 */
@Value
public class NotificationConfigFullInfoDto implements Serializable {

    Long id;

    RoleDto role;

    NotificationType notificationType;
}