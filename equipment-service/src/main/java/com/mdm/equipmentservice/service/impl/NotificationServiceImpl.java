package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.event.*;
import com.mdm.equipmentservice.mapper.*;
import com.mdm.equipmentservice.model.dto.base.EquipmentDto;
import com.mdm.equipmentservice.model.entity.*;
import com.mdm.equipmentservice.model.repository.NotificationRepository;
import com.mdm.equipmentservice.query.param.GetNotificationQueryParam;
import com.mdm.equipmentservice.query.predicate.NotificationPredicate;
import com.mdm.equipmentservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final HandoverTicketMapper handoverTicketMapper;

    private final ReportBrokenTicketMapper reportBrokenTicketMapper;

    private final InspectionTicketMapper inspectionTicketMapper;

    private final MaintenanceTicketMapper maintenanceTicketMapper;

    private final LiquidationTicketMapper liquidationTicketMapper;

    private final RepairTicketMapper repairTicketMapper;

    private final TransferTicketMapper transferTicketMapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final SimpMessagingTemplate simpMessagingTemplate;

//    public NotificationServiceImpl(NotificationRepository notificationRepository,
//                                   HandoverTicketMapper handoverTicketMapper, ReportBrokenTicketMapper reportBrokenTicketMapper,
//                                   InspectionTicketMapper inspectionTicketMapper, MaintenanceTicketMapper maintenanceTicketMapper,
//                                   LiquidationTicketMapper liquidationTicketMapper, RepairTicketMapper repairTicketMapper,
//                                   TransferTicketMapper transferTicketMapper) {
//        this.notificationRepository = notificationRepository;
//        this.handoverTicketMapper = handoverTicketMapper;
//        this.reportBrokenTicketMapper = reportBrokenTicketMapper;
//        this.inspectionTicketMapper = inspectionTicketMapper;
//        this.maintenanceTicketMapper = maintenanceTicketMapper;
//        this.liquidationTicketMapper = liquidationTicketMapper;
//        this.repairTicketMapper = repairTicketMapper;
//        this.transferTicketMapper = transferTicketMapper;
//    }

    @Override
    public Page<NotificationsFromMgdb> getAllNotification(GetNotificationQueryParam getNotificationQueryParam, Pageable pageable) {
        Pageable sortByDate = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());
        return notificationRepository.
                findAll(NotificationPredicate.getNotificationPredicate(getNotificationQueryParam), sortByDate);
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.findById(id).ifPresent(notification -> {
            notification.setIsDeleted(true);
            notificationRepository.save(notification);
        });
    }
    @Override
    @EventListener
    @Async
    @Order
    public void pushNotification(NotificationSavedEvent notificationSavedEvent) {
        NotificationsFromMgdb notification = notificationSavedEvent.getNotification();
        simpMessagingTemplate.convertAndSend("/all/notifications", notification);
    }

    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(EquipmentCreatedEvent equipmentCreatedEvent) {
        EquipmentDto equipmentDto = equipmentCreatedEvent.getEquipmentDto();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.CREATE);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setContent(String.format("""
                Nhập mới thiết bị: %s.
                """, equipmentDto.getName()));
        notification.setEquipmentId(equipmentDto.getId());
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));
    }

    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(HandoverTicketCreatedEvent handoverTicketCreatedEvent) {
        HandoverTicket handover = handoverTicketCreatedEvent.getHandoverTicket();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.HANDOVER);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setContent(String.format("""
                Phiếu bàn giao thiết bị %s đã được tạo.
                """, handover.getEquipment().getName()));
        notification.setEquipmentId(handover.getEquipment().getId());
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));
    }

    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(HandoverTicketAcceptedEvent handoverAcceptedEvent) {
        HandoverTicket handover = handoverAcceptedEvent.getHandoverTicket();
        Equipment equipment = handover.getEquipment();
        Department department = handover.getDepartment();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.HANDOVER);
        notification.setCreatedAt(LocalDateTime.now());
        String status = handover.getStatus().equals(TicketStatus.ACCEPTED) ? "được chấp nhận" : "bị từ chối";
        notification.setContent(String.format("""
                        Thiết bị %s đã %s bàn giao qua %s.
                        """,
                equipment.getName(), status, department.getName()
        ));
        notification.setEquipmentId(equipment.getId());
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));
    }

    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(ReportBrokenTicketCreatedEvent brokenReportTicketCreatedEvent) {
        ReportBrokenTicket brokenReportTicket = brokenReportTicketCreatedEvent.getReportBrokenTicket();
        Equipment equipment = brokenReportTicket.getEquipment();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.REPORT_BROKEN);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setContent(String.format("""
                        Phiếu báo hỏng thiết bị %s đã được tạo. Mức độ quan trọng: %s
                        """,
                equipment.getName(), brokenReportTicket.getPriority().toString()
        ));
        notification.setEquipmentId(equipment.getId());
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));

    }

    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(ReportBrokenTicketAcceptedEvent brokenReportTicketAcceptedEvent) {
        ReportBrokenTicket brokenReportTicket = brokenReportTicketAcceptedEvent.getReportBrokenTicket();
        Equipment equipment = brokenReportTicket.getEquipment();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.REPORT_BROKEN);
        notification.setCreatedAt(LocalDateTime.now());
        String status = brokenReportTicket.getStatus().equals(TicketStatus.ACCEPTED) ? "được báo hỏng" : "bị từ chối báo hỏng";
        notification.setContent(String.format("""
                        Thiết bị %s đã được %s. Mức độ quan trọng: %s
                        """,
                equipment.getName(), status, brokenReportTicket.getPriority().toString()
        ));
        notification.setEquipmentId(equipment.getId());
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));

    }

    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(InspectionTicketCreatedEvent inspectionTicketCreatedEvent) {
        InspectionTicket inspectionTicket = inspectionTicketCreatedEvent.getInspectionTicket();
        Equipment equipment = inspectionTicket.getEquipment();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.INSPECTION);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setContent(String.format(
                """
                        Phiếu kiểm định thiết bị %s đã được tạo .
                        """,
                equipment.getName()
        ));
        notification.setEquipmentId(equipment.getId());
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));

    }

    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(InspectionTicketUpdatedEvent inspectionTicketUpdatedEvent) {
        InspectionTicket inspectionTicket = inspectionTicketUpdatedEvent.getInspectionTicket();
        Equipment equipment = inspectionTicket.getEquipment();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.INSPECTION);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setContent(String.format(
                """
                        Phiếu kiểm định thiết bị %s đã được cập nhật.
                        """,
                equipment.getName()
        ));
        notification.setEquipmentId(equipment.getId());
        //notification.setEvent(inspectionTicketMapper.toEventDto(inspectionTicketUpdatedEvent));
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));

    }

    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(InspectionTicketAcceptedEvent inspectionTicketAcceptedEvent) {
        InspectionTicket inspectionTicket = inspectionTicketAcceptedEvent.getInspectionTicket();
        Equipment equipment = inspectionTicket.getEquipment();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.INSPECTION);
        notification.setCreatedAt(LocalDateTime.now());
        String status = inspectionTicket.getStatus().equals(TicketStatus.ACCEPTED) ? "được tạo lịch kiểm định" : "bị từ chối tạo lịch kiểm định";
        notification.setContent(String.format("""
                        Thiết bị %s đã %s.
                        """,
                equipment.getName(), status
        ));
        notification.setEquipmentId(equipment.getId());
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));
    }

    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(MaintenanceTicketCreatedEvent maintenanceTicketCreatedEvent) {
        MaintenanceTicket maintenanceTicket = maintenanceTicketCreatedEvent.getMaintenanceTicket();
        Equipment equipment = maintenanceTicket.getEquipment();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.MAINTENANCE);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setContent(String.format(
                """
                        Phiếu bảo dưỡng thiết bị %s đã được tạo.
                        """,
                equipment.getName()
        ));
        notification.setEquipmentId(equipment.getId());
        //notification.setEvent(maintenanceTicketMapper.toEventDto(maintenanceTicketCreatedEvent));
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));

    }

    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(MaintenanceTicketUpdatedEvent maintenanceTicketUpdatedEvent) {
        MaintenanceTicket maintenanceTicket = maintenanceTicketUpdatedEvent.getMaintenanceTicket();
        Equipment equipment = maintenanceTicket.getEquipment();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.MAINTENANCE);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setContent(String.format(
                """
                        Phiếu bảo dưỡng thiết bị %s đã được cập nhật.
                        """,
                equipment.getName()
        ));
        notification.setEquipmentId(equipment.getId());
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));

    }

    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(MaintenanceTicketAcceptedEvent maintenanceTicketAcceptedEvent) {
        MaintenanceTicket maintenanceTicket = maintenanceTicketAcceptedEvent.getMaintenanceTicket();
        Equipment equipment = maintenanceTicket.getEquipment();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.MAINTENANCE);
        notification.setCreatedAt(LocalDateTime.now());
        String status = maintenanceTicket.getStatus().equals(TicketStatus.ACCEPTED) ? "được tạo lịch bảo dưỡng" : "bị từ chối tạo lịch bảo dưỡng";
        notification.setContent(String.format("""
                        Thiết bị %s đã %s.
                        """,
                equipment.getName(), status
        ));
        notification.setEquipmentId(equipment.getId());
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));

    }

    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(LiquidationTicketCreatedEvent maintenanceTicketCreatedEvent) {
        LiquidationTicket liquidationTicket = maintenanceTicketCreatedEvent.getLiquidationTicket();
        Equipment equipment = liquidationTicket.getEquipment();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.LIQUIDATION);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setContent(String.format(
                """
                        Phiếu thanh lý thiết bị %s đã được tạo.
                        """,
                equipment.getName()
        ));
        notification.setEquipmentId(equipment.getId());
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));

    }

    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(LiquidationTicketAcceptedEvent maintenanceTicketAcceptedEvent) {
        LiquidationTicket liquidationTicket = maintenanceTicketAcceptedEvent.getLiquidationTicket();
        Equipment equipment = liquidationTicket.getEquipment();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.LIQUIDATION);
        notification.setCreatedAt(LocalDateTime.now());
        String status = liquidationTicket.getStatus().equals(TicketStatus.ACCEPTED) ? "được thanh lý" : "bị từ chối thanh lý";
        notification.setContent(String.format("""
                        Thiết bị %s đã %s.
                        """,
                equipment.getName(), status
        ));
        notification.setEquipmentId(equipment.getId());
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));

    }

    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(RepairTicketCreatedEvent repairTicketCreatedEvent) {
        RepairTicket repairTicket = repairTicketCreatedEvent.getRepairTicket();
        Equipment equipment = repairTicket.getEquipment();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.REPAIR);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setContent(String.format(
                """
                        Phiếu sửa chữa thiết bị %s đã được tạo.
                        """,
                equipment.getName()
        ));
        notification.setEquipmentId(equipment.getId());
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));

    }

    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(RepairTicketUpdatedEvent repairTicketUpdatedEvent) {
        RepairTicket repairTicket = repairTicketUpdatedEvent.getRepairTicket();
        Equipment equipment = repairTicket.getEquipment();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.REPAIR);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setContent(String.format(
                """
                        Phiếu sửa chữa thiết bị %s đã được cập nhật.
                        """,
                equipment.getName()
        ));
        notification.setEquipmentId(equipment.getId());
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));

    }

    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(RepairTicketAcceptedEvent repairTicketAcceptedEvent) {
        RepairTicket repairTicket = repairTicketAcceptedEvent.getRepairTicket();
        Equipment equipment = repairTicket.getEquipment();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.REPAIR);
        notification.setCreatedAt(LocalDateTime.now());
        String status = repairTicket.getStatus().equals(TicketStatus.ACCEPTED) ? "được tạo lịch sửa chữa" : "bị từ chối tạo lịch sửa chữa";
        notification.setContent(String.format("""
                        Thiết bị %s đã %s.
                        """,
                equipment.getName(), status
        ));
        notification.setEquipmentId(equipment.getId());
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));

    }

    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(RepairTicketAcceptanceTestingEvent repairTicketAcceptanceTestingEvent) {
        RepairTicket repairTicket = repairTicketAcceptanceTestingEvent.getRepairTicket();
        Equipment equipment = repairTicket.getEquipment();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.REPAIR);
        notification.setCreatedAt(LocalDateTime.now());
        String status = repairTicket.getRepairStatus().equals(RepairStatus.DONE) ? "sửa chữa xong, tình trạng sử dụng tốt. " :
                "không thể sửa chữa, đã đuợc chuyển vào kho thanh lý";
        notification.setContent(String.format("""
                        Thiết bị %s đã %s.
                        """,
                equipment.getName(), status
        ));
        notification.setEquipmentId(equipment.getId());
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));

    }

    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(TransferTicketCreatedEvent transferTicketCreatedEvent) {
        TransferTicket transferTicket = transferTicketCreatedEvent.getTransferTicket();
        Equipment equipment = transferTicket.getEquipment();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.TRANSFER);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setContent(String.format(
                """
                        Phiếu điều chuyển thiết bị %s đã được tạo.
                        """,
                equipment.getName()
        ));
        notification.setEquipmentId(equipment.getId());
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));

    }
    @Override
    @EventListener
    @Async
    @Order
    public void createNotification(TransferTicketAcceptedEvent transferTicketAcceptedEvent) {
        TransferTicket transferTicket = transferTicketAcceptedEvent.getTransferTicket();
        Equipment equipment = transferTicket.getEquipment();
        NotificationsFromMgdb notification = new NotificationsFromMgdb();
        notification.setNotificationType(NotificationType.TRANSFER);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setContent(String.format(
                """
                        Phiếu điều chuyển thiết bị %s đã được phê duyệt.
                        """,
                equipment.getName()
        ));
        notification.setEquipmentId(equipment.getId());
        notificationRepository.save(notification);
        applicationEventPublisher.publishEvent(new NotificationSavedEvent(notification));

    }

//
//    @Override
//    public void deleteMultipleNotification(List<String> ids){
//        if (ids.isEmpty())
//            return;
//        notificationMongoRepository.deleteAllById(ids);
//
//    }
//


}
