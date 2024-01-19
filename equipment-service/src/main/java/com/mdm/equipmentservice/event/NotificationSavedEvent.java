package com.mdm.equipmentservice.event;

import com.mdm.equipmentservice.model.entity.NotificationsFromMgdb;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class NotificationSavedEvent {
    private final NotificationsFromMgdb notification;
}
