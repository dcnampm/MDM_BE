package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.event.*;
import com.mdm.equipmentservice.model.entity.NotificationType;
import jakarta.mail.MessagingException;

import java.util.List;

public interface EmailService {
    void sendResetPasswordEmail(ForgotPasswordEvent event);

    void sendEmail(HandoverTicketCreatedEvent event) throws MessagingException;

    void sendEmail(HandoverTicketAcceptedEvent event) throws MessagingException;

    void sendEmail(ReportBrokenTicketCreatedEvent event) throws MessagingException;

    void sendEmail(ReportBrokenTicketAcceptedEvent event) throws MessagingException;

    void sendEmail(RepairTicketCreatedEvent event) throws MessagingException;

    void sendEmail(RepairTicketAcceptedEvent event) throws MessagingException;

    void sendEmail(RepairTicketUpdatedEvent event) throws MessagingException;

    void sendEmail(RepairTicketAcceptanceTestingEvent event) throws MessagingException;
    void sendEmail(TransferTicketCreatedEvent event) throws MessagingException;

    void sendEmail(InspectionTicketCreatedEvent event) throws MessagingException;

    void sendEmail(InspectionTicketAcceptedEvent event) throws MessagingException;

    void sendEmail(InspectionTicketUpdatedEvent event) throws MessagingException;

    void sendEmail(LiquidationTicketCreatedEvent event) throws MessagingException;

    void sendEmail(LiquidationTicketAcceptedEvent event) throws MessagingException;

    void sendEmail(MaintenanceTicketCreatedEvent event) throws MessagingException;

    void sendEmail(MaintenanceTicketAcceptedEvent event) throws MessagingException;

    void sendEmail(MaintenanceTicketUpdatedEvent event) throws MessagingException;

    List<String> getAllEmailThatCanReceiveNotification(NotificationType notificationType);
}
