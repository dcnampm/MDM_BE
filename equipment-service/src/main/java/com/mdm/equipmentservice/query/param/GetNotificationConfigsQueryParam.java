package com.mdm.equipmentservice.query.param;

import com.mdm.equipmentservice.model.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetNotificationConfigsQueryParam {
    private String roleName;
    private NotificationType notificationType;
}
