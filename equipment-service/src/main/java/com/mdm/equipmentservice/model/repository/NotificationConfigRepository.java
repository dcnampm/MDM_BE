package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.NotificationConfig;
import com.mdm.equipmentservice.model.entity.NotificationType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface NotificationConfigRepository extends ParentRepository<NotificationConfig, Long> {

    @EntityGraph(value = "notificationConfigWithRole")
    Set<NotificationConfig> findByNotificationType(NotificationType notificationType);

}