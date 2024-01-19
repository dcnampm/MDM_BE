package com.mdm.equipmentservice.query.param;

import com.mdm.equipmentservice.model.entity.NotificationType;
import lombok.Data;

@Data
public class GetNotificationQueryParam {
    private Long equipmentId;
    private NotificationType notificationType;
    private Boolean isDeleted;
}
