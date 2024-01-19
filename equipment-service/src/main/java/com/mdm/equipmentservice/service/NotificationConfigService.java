package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.form.UpdateNotificationConfigForm;
import com.mdm.equipmentservice.model.dto.fullinfo.NotificationConfigFullInfoDto;
import com.mdm.equipmentservice.model.entity.NotificationType;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Set;

public interface NotificationConfigService {

    List<String> getAllRoleThatCanReceiveNotification(NotificationType notificationType);
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TPVT')")
    void updateNotificationConfig(Set<UpdateNotificationConfigForm> updateNotificationConfigForm);

    List<NotificationConfigFullInfoDto> getNotificationConfigs();
}
