package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.event.*;
import com.mdm.equipmentservice.model.entity.NotificationsFromMgdb;
import com.mdm.equipmentservice.query.param.GetNotificationQueryParam;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {

    Page<NotificationsFromMgdb> getAllNotification(GetNotificationQueryParam getNotificationQueryParam, Pageable pageable);

    void deleteNotification(Long id);

    void pushNotification(NotificationSavedEvent notificationSavedEvent);

    void createNotification(EquipmentCreatedEvent equipmentCreatedEvent);

    void createNotification(HandoverTicketCreatedEvent handoverTicketCreatedEvent);

    void createNotification(HandoverTicketAcceptedEvent handoverTicketAcceptedEvent);

    void createNotification(ReportBrokenTicketCreatedEvent reportBrokenTicketCreatedEvent);

    void createNotification(ReportBrokenTicketAcceptedEvent reportBrokenTicketAcceptedEvent);

    void createNotification(InspectionTicketCreatedEvent inspectionTicketCreatedEvent);

    void createNotification(InspectionTicketUpdatedEvent inspectionTicketUpdatedEvent);

    void createNotification(InspectionTicketAcceptedEvent inspectionTicketAcceptedEvent);

    void createNotification(MaintenanceTicketCreatedEvent maintenanceTicketCreatedEvent);

    void createNotification(MaintenanceTicketUpdatedEvent maintenanceTicketUpdatedEvent);

    void createNotification(MaintenanceTicketAcceptedEvent maintenanceTicketAcceptedEvent);

    void createNotification(LiquidationTicketCreatedEvent liquidationTicketCreatedEvent);

    void createNotification(LiquidationTicketAcceptedEvent liquidationTicketAcceptedEvent);

    void createNotification(RepairTicketCreatedEvent repairTicketCreatedEvent);

    void createNotification(RepairTicketUpdatedEvent repairTicketUpdatedEvent);

    void createNotification(RepairTicketAcceptedEvent repairTicketAcceptedEvent);

    void createNotification(RepairTicketAcceptanceTestingEvent repairTicketAcceptanceTestingEvent);

    void createNotification(TransferTicketCreatedEvent transferTicketCreatedEvent);
    void createNotification(TransferTicketAcceptedEvent transferTicketAcceptedEvent);
}