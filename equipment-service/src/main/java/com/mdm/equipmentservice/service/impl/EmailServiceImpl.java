package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.event.*;
import com.mdm.equipmentservice.model.dto.fullinfo.UserDetailDto;
import com.mdm.equipmentservice.model.entity.*;
import com.mdm.equipmentservice.model.repository.NotificationConfigRepository;
import com.mdm.equipmentservice.model.repository.UserRepository;
import com.mdm.equipmentservice.service.EmailService;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.service.NotificationConfigService;
import com.mdm.equipmentservice.service.UserService;
import com.mdm.equipmentservice.util.CommonUtil;
import com.mdm.equipmentservice.util.MessageUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mdm.equipmentservice.constant.DateTimeFormatConstant.DATE_TIME_FORMAT;


@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final NotificationConfigRepository notificationConfigRepository;

    private final UserRepository userRepository;

    private final JavaMailSender mailSender;

    private final MessageUtil messageUtil;

    private final UserService userService;

    @Value("${spring.mail.username}")
    private String from;

    private final FileStorageService fileStorageService;

    private final NotificationConfigService notificationConfigService;

    @Autowired
    public EmailServiceImpl(
            JavaMailSender mailSender, MessageUtil messageUtil, UserService userService, FileStorageService fileStorageService,
            NotificationConfigService notificationConfigService,
            UserRepository userRepository,
            NotificationConfigRepository notificationConfigRepository) {
        this.mailSender = mailSender;
        this.messageUtil = messageUtil;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
        this.notificationConfigService = notificationConfigService;
        this.userRepository = userRepository;
        this.notificationConfigRepository = notificationConfigRepository;
    }

    @EventListener
    @Async
    @Override
    public void sendResetPasswordEmail(ForgotPasswordEvent event) {
        log.info("Sending reset password to {}", event.getEmail());
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(from);
        email.setTo(event.getEmail());
        email.setSubject(messageUtil.getMessage("forgotPassword"));
        email.setText(messageUtil.getMessage(
                "pleaseClickThisLinkToResetYourPassword",
                event.getRemoteOrigin() + "/reset-password/confirm?uuid=" + event.getForgotPassword().getUuid()
        ));
        mailSender.send(email);
    }

    @Override
    @EventListener
    @Async
    public void sendEmail(HandoverTicketCreatedEvent event) throws MessagingException {
        log.info("Sending createHandoverTicket ticket created email...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        createTemplate(event, helper);
        mailSender.send(message);
    }


    @Override
    @Async
    @EventListener
    public void sendEmail(HandoverTicketAcceptedEvent event) throws MessagingException {
        log.info("Sending createHandoverTicket accepted email...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        createTemplate(event, helper);
        mailSender.send(message);
    }

    @Override
    @Async
    @EventListener
    public void sendEmail(ReportBrokenTicketCreatedEvent event) throws MessagingException {
        log.info("sending broken report ticket created email...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        createTemplate(event, helper);
        mailSender.send(message);
    }

    @Override
    @Async
    @EventListener
    public void sendEmail(ReportBrokenTicketAcceptedEvent event) throws MessagingException {
        log.info("sending broken report ticket accepted email...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        createTemplate(event, helper);
        mailSender.send(message);
    }

    @Override
    @Async
    @EventListener
    public void sendEmail(RepairTicketCreatedEvent event) throws MessagingException {
        log.info("sending repair ticket created email...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        createTemplate(event, helper);
        mailSender.send(message);
    }

    @Override
    @Async
    @EventListener
    public void sendEmail(RepairTicketAcceptedEvent event) throws MessagingException {
        log.info("sending repair ticket accepted email...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        createTemplate(event, helper);
        mailSender.send(message);
    }

    @Override
    @Async
    @EventListener
    public void sendEmail(RepairTicketUpdatedEvent event) throws MessagingException {
        log.info("sending repair ticket updated email...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        createTemplate(event, helper);
        mailSender.send(message);
    }

    @Override
    @Async
    @EventListener
    public void sendEmail(RepairTicketAcceptanceTestingEvent event) throws MessagingException {
        log.info("sending repair ticket acceptance testing email...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        createTemplate(event, helper);
        mailSender.send(message);
    }

    @Override
    @Async
    @EventListener
    public void sendEmail(TransferTicketCreatedEvent event) throws MessagingException {
        log.info("sending transfer ticket created email...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        createTemplate(event, helper);
        mailSender.send(message);
    }


    @Override
    @Async
    @EventListener
    public void sendEmail(LiquidationTicketCreatedEvent event) throws MessagingException {
        log.info("sending transfer ticket created email...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        createTemplate(event, helper);
        mailSender.send(message);
    }

    @Override
    @Async
    @EventListener
    public void sendEmail(LiquidationTicketAcceptedEvent event) throws MessagingException {
        log.info("sending transfer ticket created email...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        createTemplate(event, helper);
        mailSender.send(message);
    }

    @Override
    @Async
    @EventListener
    public void sendEmail(MaintenanceTicketCreatedEvent event) throws MessagingException {
        log.info("sending transfer ticket created email...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        createTemplate(event, helper);
        mailSender.send(message);
    }

    @Override
    @Async
    @EventListener
    public void sendEmail(MaintenanceTicketUpdatedEvent event) throws MessagingException {
        log.info("sending transfer ticket created email...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        createTemplate(event, helper);
        mailSender.send(message);
    }

    @Override
    @Async
    @EventListener
    public void sendEmail(MaintenanceTicketAcceptedEvent event) throws MessagingException {
        log.info("sending transfer ticket created email...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        createTemplate(event, helper);
        mailSender.send(message);
    }
    @Override
    @Async
    @EventListener
    public void sendEmail(InspectionTicketCreatedEvent event) throws MessagingException {
        log.info("sending inspection ticket created email...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        createTemplate(event, helper);
        mailSender.send(message);
    }

    @Override
    @Async
    @EventListener
    public void sendEmail(InspectionTicketUpdatedEvent event) throws MessagingException {
        log.info("sending inspection ticket created email...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        createTemplate(event, helper);
        mailSender.send(message);
    }

    @Override
    @Async
    @EventListener
    public void sendEmail(InspectionTicketAcceptedEvent event) throws MessagingException {
        log.info("sending inspection ticket created email...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        createTemplate(event, helper);
        mailSender.send(message);
    }




    /**
     * get all email that can receive notification, based on notification type
     *
     * @param notificationType notification type to get all email that can receive notification
     * @return list of email that can receive notification
     */
    @Override
    public List<String> getAllEmailThatCanReceiveNotification(NotificationType notificationType) {
        List<String> allRoleThatCanReceiveNotification = notificationConfigService.getAllRoleThatCanReceiveNotification(notificationType);
        List<UserDetailDto> allUserInRoles = userService.getAllUserInRoles(allRoleThatCanReceiveNotification);
        return allUserInRoles.stream().map(UserDetailDto::getEmail).toList();
    }

    private void createTemplate(ReportBrokenTicketAcceptedEvent event, MimeMessageHelper helper) throws MessagingException {
        ReportBrokenTicket reportBrokenTicket = event.getReportBrokenTicket();
        Equipment equipment = reportBrokenTicket.getEquipment();
        Department department = equipment.getDepartment();
        User creator = reportBrokenTicket.getCreator();
        User approver = reportBrokenTicket.getApprover();
        List<FileStorage> documents = fileStorageService.getAllFilesOfAnEntity(
                reportBrokenTicket.getClass().getSimpleName(),
                reportBrokenTicket.getId(),
                FileDescription.DOCUMENT
        );
        helper.setFrom(from);
        helper.setTo(creator.getEmail());
        helper.setCc(getCcEmails(NotificationType.REPORT_BROKEN, creator.getEmail()));
        String title = reportBrokenTicket.getStatus().equals(TicketStatus.ACCEPTED) ? "được chấp nhận" : "bị từ chối";
        String subject = reportBrokenTicket.getStatus().equals(TicketStatus.ACCEPTED) ? "Chấp nhận" : "Từ chối";
        helper.setSubject(String.format("""
                [Báo hỏng][%s] Phiếu báo hỏng thiết bị [%s] %s.
                """, subject, equipment.getName(), title));
        helper.setText(String.format(
                """
                        Phiếu báo hỏng thiết bị: [%s] đã %s.
                        Thiết bị đã được chuyển sang trạng thái "Hỏng" và cần lên lịch sửa chữa.
                        Serial: [%s]
                        Model: [%s]
                        Mã thiết bị: [%s]
                        Phòng ban: [%s]
                        Người báo hỏng: [%s]
                        Lý do: [%s]
                        Ngày báo hỏng: [%s]
                        Người phê duyệt: [%s]
                        Ngày phê duyệt: [%s]
                        Ghi chú của người phê duyệt: [%s]
                        """,
                equipment.getName(),
                title,
                equipment.getSerial(),
                equipment.getModel(),
                equipment.getHashCode(),
                department.getName(),
                creator.getName(),
                reportBrokenTicket.getReason(),
                reportBrokenTicket.getCreatedDate().format(DATE_TIME_FORMAT),
                approver.getName(),
                reportBrokenTicket.getApprovalDate().format(DATE_TIME_FORMAT),
                reportBrokenTicket.getApproverNote()
        ));
        addAttachment(helper, documents);
    }

    private void createTemplate(HandoverTicketCreatedEvent event, MimeMessageHelper helper) throws MessagingException {
        HandoverTicket handoverTicket = event.getHandoverTicket();
        Equipment equipment = handoverTicket.getEquipment();
        Department department = equipment.getDepartment();
        User responsiblePerson = handoverTicket.getResponsiblePerson();
        User createdBy = handoverTicket.getCreator();
        List<FileStorage> documents =
                fileStorageService.getAllFilesOfAnEntity(handoverTicket.getClass().getSimpleName(), handoverTicket.getId(), FileDescription.DOCUMENT);
        helper.setFrom(from);
        helper.setTo(createdBy.getEmail());
        helper.setCc(getCcEmails(NotificationType.HANDOVER, createdBy.getEmail()));
        helper.setSubject(String.format("""
                [Bàn giao] Phiếu bàn giao thiết bị [%s] đã được tạo.
                """, equipment.getName()));
        helper.setText(String.format(
                """
                        Phiếu bàn giao thiết bị: [%s] đã được tạo. Thiết bị đã được chuyển sang danh sách: [Chờ duyệt bàn giao].
                        Serial: [%s]
                        Model: [%s]
                        Mã thiết bị: [%s]
                        Khoa phòng nhận bàn giao: [%s]
                        Người phụ trách: [%s]
                        Người tạo phiếu bàn giao: [%s]
                        Ngày tạo phiếu bàn giao: [%s]
                        Ghi chú: [%s]
                        """,
                equipment.getName(),
                equipment.getSerial(),
                equipment.getModel(),
                equipment.getHashCode(),
                department.getName(),
                responsiblePerson.getName(),
                createdBy.getName(),
                handoverTicket.getHandoverDate().format(DATE_TIME_FORMAT),
                handoverTicket.getHandoverNote()
        ));
        addAttachment(helper, documents);
    }

    private void createTemplate(ReportBrokenTicketCreatedEvent event, MimeMessageHelper helper) throws MessagingException {
        ReportBrokenTicket reportBrokenTicket = event.getReportBrokenTicket();
        Equipment equipment = reportBrokenTicket.getEquipment();
        Department department = equipment.getDepartment();
        User createdUser = reportBrokenTicket.getCreator();
        List<FileStorage> documents = fileStorageService.getAllFilesOfAnEntity(
                reportBrokenTicket.getClass().getSimpleName(),
                reportBrokenTicket.getId(),
                FileDescription.DOCUMENT
        );
        helper.setFrom(from);
        helper.setTo(createdUser.getEmail());
        helper.setCc(getCcEmails(NotificationType.REPORT_BROKEN, createdUser.getEmail()));
        helper.setSubject(String.format("""
                [Báo hỏng] Phiếu báo hỏng thiết bị [%s] đã được tạo. Mã phiếu báo hỏng: [%s]
                """, equipment.getName(), reportBrokenTicket.getCode()));
        helper.setText(String.format(
                """
                        Phiếu báo hỏng thiết bị: [%s] đã được tạo. Thiết bị đã được chuyển sang danh sách: [Chờ duyệt báo hỏng].
                        Serial: [%s]
                        Model: [%s]
                        Mã thiết bị: [%s]
                        Khoa phòng sở hữu: [%s]
                        Người tạo phiếu báo hỏng: [%s]
                        Ngày tạo phiếu báo hỏng: [%s]
                        Ly do báo hỏng: [%s]
                        Mức độ ưu tiên: [%s]
                        """,
                equipment.getName(),
                equipment.getSerial(),
                equipment.getModel(),
                equipment.getHashCode(),
                department.getName(),
                createdUser.getName(),
                reportBrokenTicket.getCreatedDate().format(DATE_TIME_FORMAT),
                reportBrokenTicket.getReason(),
                messageUtil.getMessage(reportBrokenTicket.getPriority().toString())
        ));
        addAttachment(helper, documents);
    }

    private void createTemplate(HandoverTicketAcceptedEvent event, MimeMessageHelper helper) throws MessagingException {
        HandoverTicket handoverTicket = event.getHandoverTicket();
        Equipment equipment = handoverTicket.getEquipment();
        Department department = handoverTicket.getDepartment();
        User responsiblePerson = handoverTicket.getResponsiblePerson();
        User createdBy = handoverTicket.getCreator();
        User approver = handoverTicket.getApprover();
        List<FileStorage> documents =
                fileStorageService.getAllFilesOfAnEntity(handoverTicket.getClass().getSimpleName(), handoverTicket.getId(), FileDescription.DOCUMENT);
        helper.setFrom(from);
        helper.setTo(new String[]{createdBy.getEmail(), approver.getEmail()});
        helper.setCc(getCcEmails(NotificationType.HANDOVER, createdBy.getEmail(), approver.getEmail()));
        String title = handoverTicket.getStatus() == TicketStatus.ACCEPTED ? "được chấp nhận" : "bị từ chối";
        String subject = handoverTicket.getStatus() == TicketStatus.ACCEPTED ? "Chấp nhận" : "Từ chối";
        helper.setSubject(String.format("""
                [Bàn giao][%s] Phiếu bàn giao thiết bị [%s] đã %s
                """, subject, equipment.getName(), title));
        helper.setText(String.format(
                """
                        Phiếu bàn giao thiết bị: [%s] đã %s.
                        Serial: [%s]
                        Model: [%s]
                        Mã thiết bị: [%s]
                        Khoa phòng nhận bàn giao: [%s]
                        Người phụ trách: [%s]
                        Người tạo phiếu bàn giao: [%s]
                        Ngày tạo phiếu bàn giao: [%s]
                        Ghi chú: [%s]
                        Người phê duyệt: [%s]
                        Ngày phê duyệt: [%s]
                        Ghi chú của người phê duyệt: [%s]
                        """,
                equipment.getName(),
                title,
                equipment.getSerial(),
                equipment.getModel(),
                equipment.getHashCode(),
                department.getName(),
                responsiblePerson.getName(),
                createdBy.getName(),
                handoverTicket.getHandoverDate().format(DATE_TIME_FORMAT),
                handoverTicket.getHandoverNote(),
                approver.getName(),
                handoverTicket.getApprovalDate().format(DATE_TIME_FORMAT),
                handoverTicket.getApproverNote()
        ));
        addAttachment(helper, documents);
    }


    private void createTemplate(RepairTicketCreatedEvent event, MimeMessageHelper helper) throws MessagingException {
        RepairTicket repairTicket = event.getRepairTicket();
        Equipment equipment = repairTicket.getEquipment();
        Department department = equipment.getDepartment();
        User createdUser = repairTicket.getCreator();
        List<FileStorage> documents = fileStorageService.getAllFilesOfAnEntity(
                repairTicket.getClass().getSimpleName(),
                repairTicket.getId(),
                FileDescription.DOCUMENT
        );
        helper.setFrom(from);
        helper.setTo(createdUser.getEmail());
        helper.setCc(getCcEmails(NotificationType.REPAIR, createdUser.getEmail()));
        helper.setSubject(String.format("""
                [Sửa chữa] Phiếu sửa chữa thiết bị [%s] đã được tạo. Mã phiếu sửa chữa: [%s]
                """, equipment.getName(), repairTicket.getCode()));
        helper.setText(String.format(
                """
                        Phiếu sửa chữa thiết bị: [%s] đã được tạo. Thiết bị đã được chuyển sang danh sách: [Chờ duyệt sửa chữa].
                        Serial: [%s]
                        Model: [%s]
                        Mã thiết bị: [%s]
                        Khoa phòng sở hữu: [%s]
                        Người tạo phiếu sửa chữa: [%s]
                        Ngày tạo phiếu sửa chữa: [%s]
                        Ngày sửa chữa: [%s]
                        Chi phí dự kiến: [%s]
                        Ghi chú: [%s]
                        Nhà cung cấp: [%s]
                        """,
                equipment.getName(),
                equipment.getSerial(),
                equipment.getModel(),
                equipment.getHashCode(),
                department.getName(),
                createdUser.getName(),
                repairTicket.getCreatedDate().format(DATE_TIME_FORMAT),
                repairTicket.getRepairStartDate().format(DATE_TIME_FORMAT),
                CommonUtil.convertToCurrency(repairTicket.getEstimatedCost().toString()),
                repairTicket.getCreatorNote(),
                ObjectUtils.isNotEmpty(repairTicket.getRepairCompany()) ? repairTicket.getRepairCompany().getName() : ""
        ));
        addAttachment(helper, documents);
    }


    private void createTemplate(RepairTicketAcceptedEvent event, MimeMessageHelper helper) throws MessagingException {
        RepairTicket repairTicket = event.getRepairTicket();
        Equipment equipment = repairTicket.getEquipment();
        Department department = equipment.getDepartment();
        User creator = repairTicket.getCreator();
        User approver = repairTicket.getApprover();
        List<FileStorage> documents = fileStorageService.getAllFilesOfAnEntity(
                repairTicket.getClass().getSimpleName(),
                repairTicket.getId(),
                FileDescription.DOCUMENT
        );
        helper.setFrom(from);
        helper.setTo(creator.getEmail());
        helper.setCc(getCcEmails(NotificationType.REPAIR, creator.getEmail()));
        String title = repairTicket.getStatus().equals(TicketStatus.ACCEPTED) ? "được chấp nhận" : "bị từ chối";
        String subject = repairTicket.getStatus().equals(TicketStatus.ACCEPTED) ? "Chấp nhận" : "Từ chối";
        helper.setSubject(String.format("""
                [Sửa chữa][%s] Phiếu yêu cầu sửa chữa thiết bị [%s] %s.
                """, subject, equipment.getName(), title));
        helper.setText(String.format(
                """
                        Phiếu yêu cầu sửa chữa thiết bị: [%s] đã %s.
                        Thiết bị đã được chuyển sang trạng thái "Đang sửa chữa".
                        Serial: [%s]
                        Model: [%s]
                        Mã thiết bị: [%s]
                        Phòng ban: [%s]
                        Người tạo phiếu: [%s]
                        Ngày tạo phiếu: [%s]
                        Ghi chú của người tạo phiếu: [%s]
                        Chi phí sửa chữa dự kiến: [%s]
                        Ngày bắt đầu sửa chữa: [%s]
                        Công ty sửa chữa: [%s]
                        Người phê duyệt: [%s]
                        Ngày phê duyệt: [%s]
                        Ghi chú của người phê duyệt: [%s]
                        """,
                equipment.getName(),
                title,
                equipment.getSerial(),
                equipment.getModel(),
                equipment.getHashCode(),
                department.getName(),
                creator.getName(),
                repairTicket.getCreatedDate().format(DATE_TIME_FORMAT),
                repairTicket.getCreatorNote(),
                CommonUtil.convertToCurrency(repairTicket.getEstimatedCost().toString()),
                repairTicket.getRepairStartDate().format(DATE_TIME_FORMAT),
                ObjectUtils.isNotEmpty(repairTicket.getRepairCompany()) ? repairTicket.getRepairCompany().getName() : "",
                approver.getName(),
                repairTicket.getApprovalDate().format(DATE_TIME_FORMAT),
                repairTicket.getApproverNote()
        ));
        addAttachment(helper, documents);
    }

    private void createTemplate(RepairTicketUpdatedEvent event, MimeMessageHelper helper) throws MessagingException {
        RepairTicket repairTicket = event.getRepairTicket();
        Equipment equipment = repairTicket.getEquipment();
        Department department = equipment.getDepartment();
        User creator = repairTicket.getCreator();
        List<FileStorage> documents = fileStorageService.getAllFilesOfAnEntity(
                repairTicket.getClass().getSimpleName(),
                repairTicket.getId(),
                FileDescription.DOCUMENT
        );
        helper.setFrom(from);
        helper.setTo(creator.getEmail());
        helper.setCc(getCcEmails(NotificationType.REPAIR, creator.getEmail()));
        helper.setSubject(String.format("""
                [Sửa chữa][Cập nhật] Phiếu sửa chữa thiết bị [%s] đã được cập nhật.
                """, equipment.getName()));
        helper.setText(String.format(
                """
                        Phiếu sửa chữa thiết bị: [%s] đã được cập nhật.
                        Trạng thái sửa chữa: [%s]
                        Thiết bị đã được chuyển sang trạng thái "Chờ nghiệm thu".
                        Serial: [%s]
                        Model: [%s]
                        Mã thiết bị: [%s]
                        Phòng ban: [%s]
                        Ngày tạo phiếu: [%s]
                        Ghi chú của người tạo phiếu: [%s]
                        Chi phí sửa chữa dự kiến: [%s]
                        Chi phí sửa chữa thực tế: [%s]
                        Ngày bắt đầu sửa chữa: [%s]
                        Ngày kết thúc sửa chữa: [%s]
                        Công ty sửa chữa: [%s]
                        Ghi chú: [%s]
                        """,
                equipment.getName(),
                messageUtil.getMessage(repairTicket.getRepairStatus().toString()),
                equipment.getSerial(),
                equipment.getModel(),
                equipment.getHashCode(),
                department.getName(),
                repairTicket.getCreatedDate().format(DATE_TIME_FORMAT),
                repairTicket.getCreatorNote(),
                CommonUtil.convertToCurrency(repairTicket.getEstimatedCost().toString()),
                CommonUtil.convertToCurrency(repairTicket.getActualCost().toString()),
                repairTicket.getRepairStartDate().format(DATE_TIME_FORMAT),
                repairTicket.getRepairEndDate().format(DATE_TIME_FORMAT),
                ObjectUtils.isNotEmpty(repairTicket.getRepairCompany()) ? repairTicket.getRepairCompany().getName() : "",
                repairTicket.getRepairNote()
        ));
        addAttachment(helper, documents);
    }

    private void createTemplate(RepairTicketAcceptanceTestingEvent event, MimeMessageHelper helper) throws MessagingException {
        RepairTicket repairTicket = event.getRepairTicket();
        Equipment equipment = repairTicket.getEquipment();
        Department department = equipment.getDepartment();
        User creator = repairTicket.getCreator();
        User acceptanceTester = repairTicket.getAcceptanceTester();
        String status = equipment.getStatus().equals(EquipmentStatus.IN_USE) ? "Đang sử dụng" : "Ngừng sử dụng";
        List<FileStorage> documents = fileStorageService.getAllFilesOfAnEntity(
                repairTicket.getClass().getSimpleName(),
                repairTicket.getId(),
                FileDescription.DOCUMENT
        );
        helper.setFrom(from);
        helper.setTo(creator.getEmail());
        helper.setCc(getCcEmails(NotificationType.REPAIR, creator.getEmail()));
        helper.setSubject(String.format("""
                [Sửa chữa][Nghiệm thu] Phiếu sửa chữa thiết bị [%s] đã được nghiệm thu.
                """, equipment.getName()));
        helper.setText(String.format(
                """
                        Phiếu sửa chữa thiết bị: [%s] đã được nghiệm thu.
                        Trạng thái sửa chữa: [%s]
                        Thiết bị đã được chuyển sang trạng thái "%s".
                        Serial: [%s]
                        Model: [%s]
                        Mã thiết bị: [%s]
                        Phòng ban: [%s]
                        Ngày tạo phiếu: [%s]
                        Ghi chú của người tạo phiếu: [%s]
                        Chi phí sửa chữa dự kiến: [%s]
                        Chi phí sửa chữa thực tế: [%s]
                        Ngày bắt đầu sửa chữa: [%s]
                        Ngày kết thúc sửa chữa: [%s]
                        Công ty sửa chữa: [%s]
                        Ghi chú sửa chữa: [%s]
                        Người nghiệm thu: [%s]
                        """,
                equipment.getName(),
                messageUtil.getMessage(repairTicket.getRepairStatus().toString()),
                status,
                equipment.getSerial(),
                equipment.getModel(),
                equipment.getHashCode(),
                department.getName(),
                repairTicket.getCreatedDate().format(DATE_TIME_FORMAT),
                repairTicket.getCreatorNote(),
                CommonUtil.convertToCurrency(repairTicket.getEstimatedCost().toString()),
                CommonUtil.convertToCurrency(repairTicket.getActualCost().toString()),
                repairTicket.getRepairStartDate().format(DATE_TIME_FORMAT),
                repairTicket.getRepairEndDate().format(DATE_TIME_FORMAT),
                ObjectUtils.isNotEmpty(repairTicket.getRepairCompany()) ? repairTicket.getRepairCompany().getName() : "",
                repairTicket.getRepairNote(),
                acceptanceTester.getName()
        ));
        addAttachment(helper, documents);
    }

    private void createTemplate(TransferTicketCreatedEvent event, MimeMessageHelper helper) throws MessagingException {
        TransferTicket transferTicket = event.getTransferTicket();
        Equipment equipment = transferTicket.getEquipment();
        Department department = equipment.getDepartment();
        User createdUser = transferTicket.getCreator();
        List<FileStorage> documents = fileStorageService.getAllFilesOfAnEntity(
                transferTicket.getClass().getSimpleName(),
                transferTicket.getId(),
                FileDescription.DOCUMENT
        );
        helper.setFrom(from);
        helper.setTo(createdUser.getEmail());
        helper.setCc(getCcEmails(NotificationType.REPORT_BROKEN, createdUser.getEmail()));
        helper.setSubject(String.format("""
                [Điều chuyển] Phiếu điều chuyển thiết bị [%s] đã được tạo. Mã phiếu: [%s]
                """, equipment.getName(), transferTicket.getCode()));
        helper.setText(String.format(
                """
                        Phiếu điều chuyển thiết bị: [%s] đã được tạo. Thiết bị đã được chuyển sang danh sách: [Chờ duyệt điều chuyển].
                        Serial: [%s]
                        Model: [%s]
                        Mã thiết bị: [%s]
                        Khoa phòng sở hữu: [%s]
                        Khoa phòng nhận điều chuyển: [%s]
                        Người tạo phiếu: [%s]
                        Ngày tạo phiếu: [%s]
                        Ngày điều chuyển: [%s]
                        Ghi chú: [%s]
                        Ghi chú của người tạo phiếu: [%s]
                        """,
                equipment.getName(),
                equipment.getSerial(),
                equipment.getModel(),
                equipment.getHashCode(),
                department.getName(),
                transferTicket.getToDepartment().getName(),
                createdUser.getName(),
                transferTicket.getCreatedDate().format(DATE_TIME_FORMAT),
                transferTicket.getDateTransfer().format(DATE_TIME_FORMAT),
                transferTicket.getTransferNote(),
                transferTicket.getCreatorNote()
        ));
        addAttachment(helper, documents);
    }

    private void createTemplate(LiquidationTicketCreatedEvent event, MimeMessageHelper helper) throws MessagingException {
        LiquidationTicket liquidationTicket = event.getLiquidationTicket();
        Equipment equipment = liquidationTicket.getEquipment();
        User creator = liquidationTicket.getCreator();
        Department department = equipment.getDepartment();
        List<FileStorage> documents = fileStorageService.getAllFilesOfAnEntity(
                liquidationTicket.getClass().getSimpleName(),
                liquidationTicket.getId(),
                FileDescription.DOCUMENT
        );
        helper.setFrom(from);
        helper.setTo(creator.getEmail());
        helper.setCc(getCcEmails(NotificationType.LIQUIDATION, creator.getEmail()));
        helper.setSubject(String.format("""
                [Thanh lý] Phiếu thanh lý thiết bị [%s] đã được tạo.
                """, equipment.getName()));
        helper.setText(String.format(
                """
                        Phiếu thanh lý thiết bị: [%s] đã được tạo. Thiết bị đã được chuyển sang danh sách: [Chờ duyệt thanh lý].
                        Serial: [%s]
                        Model: [%s]
                        Mã thiết bị: [%s]
                        Khoa Phòng ban: [%s]
                        Ngày thanh lý: [%s] 
                        Giá thanh lý: [%s]  
                        Người tạo phiếu thanh lý: [%s]
                        Ngày tạo phiếu thanh lý: [%s]
                        Ghi chú: [%s]
                        """,
                equipment.getName(),
                equipment.getSerial(),
                equipment.getModel(),
                equipment.getHashCode(),
                department.getName(),
                liquidationTicket.getLiquidationDate().format(DATE_TIME_FORMAT),
                liquidationTicket.getPrice(),
                creator.getName(),
                liquidationTicket.getCreatedDate().format(DATE_TIME_FORMAT),
                liquidationTicket.getLiquidationNote()
        ));
        addAttachment(helper, documents);
    }

    private void createTemplate(LiquidationTicketAcceptedEvent event, MimeMessageHelper helper) throws MessagingException {
        LiquidationTicket liquidationTicket = event.getLiquidationTicket();
        Equipment equipment = liquidationTicket.getEquipment();
        User creator = liquidationTicket.getCreator();
        User approver = liquidationTicket.getApprover();
        Department department = equipment.getDepartment();
        List<FileStorage> documents = fileStorageService.getAllFilesOfAnEntity(
                liquidationTicket.getClass().getSimpleName(),
                liquidationTicket.getId(),
                FileDescription.DOCUMENT
        );
        helper.setFrom(from);
        helper.setTo(creator.getEmail());
        helper.setCc(getCcEmails(NotificationType.LIQUIDATION, creator.getEmail()));
        String title = liquidationTicket.getStatus().equals(TicketStatus.ACCEPTED) ? "được chấp nhận" : "bị từ chối";
        String subject = liquidationTicket.getStatus().equals(TicketStatus.ACCEPTED) ? "Chấp nhận" : "Từ chối";
        String status = liquidationTicket.getStatus().equals(TicketStatus.ACCEPTED) ? "Đã thanh lý" : "Chờ duyệt thanh lý";
        helper.setSubject(String.format("""
                [Thanh lý][%s] Phiếu thanh lý thiết bị [%s] %s.
                """, subject, equipment.getName(), title));
        helper.setText(String.format(
                """
                        Phiếu thanh lý thiết bị: [%s] đã %s.
                        Thiết bị đã được chuyển sang trạng thái [%s].
                        Serial: [%s]
                        Model: [%s]
                        Mã thiết bị: [%s]
                        Khoa Phòng ban: [%s]
                        Ngày thanh lý: [%s]
                        Giá thanh lý: [%s] 
                        Người tạo phiếu thanh lý: [%s]
                        Ngày tạo phiếu thanh lý: [%s]
                        Người phê duyệt: [%s]
                        Ngày phê duyệt: [%s]
                        Ghi chú của người phê duyệt: [%s]
                        """,
                equipment.getName(),
                title,
                status,
                equipment.getSerial(),
                equipment.getModel(),
                equipment.getHashCode(),
                department.getName(),
                liquidationTicket.getLiquidationDate().format(DATE_TIME_FORMAT),
                liquidationTicket.getPrice(),
                creator.getName(),
                liquidationTicket.getCreatedDate().format(DATE_TIME_FORMAT),
                approver.getName(),
                liquidationTicket.getApprovalDate().format(DATE_TIME_FORMAT),
                liquidationTicket.getApproverNote()
        ));
        addAttachment(helper, documents);
    }

    private void createTemplate(MaintenanceTicketCreatedEvent event, MimeMessageHelper helper) throws MessagingException {
        MaintenanceTicket maintenanceTicket = event.getMaintenanceTicket();
        Equipment equipment = maintenanceTicket.getEquipment();
        User creator = maintenanceTicket.getCreator();
        Department department = equipment.getDepartment();
        List<FileStorage> documents = fileStorageService.getAllFilesOfAnEntity(
                maintenanceTicket.getClass().getSimpleName(),
                maintenanceTicket.getId(),
                FileDescription.DOCUMENT
        );
        helper.setFrom(from);
        helper.setTo(creator.getEmail());
        helper.setCc(getCcEmails(NotificationType.MAINTENANCE, creator.getEmail()));
        helper.setSubject(String.format("""
                [Bảo trì] Phiếu bảo trì thiết bị [%s] đã được tạo.
                """, equipment.getName()));
        helper.setText(String.format(
                """
                        Phiếu bảo trì thiết bị: [%s] đã được tạo. Thiết bị đã được chuyển sang danh sách: [Chờ duyệt bảo trì].
                        Serial: [%s]
                        Model: [%s]
                        Mã thiết bị: [%s]
                        Khoa Phòng ban: [%s]
                        Ngày bảo trì: [%s]
                        Đơn vị thực hiện bảo trì: [%s]
                        Người tạo phiếu bảo trì: [%s]
                        Ngày tạo phiếu bảo trì: [%s]
                        Ghi chú: [%s]
                        """,
                equipment.getName(),
                equipment.getSerial(),
                equipment.getModel(),
                equipment.getHashCode(),
                department.getName(),
                maintenanceTicket.getMaintenanceDate().format(DATE_TIME_FORMAT),
                maintenanceTicket.getMaintenanceCompany().getName(),
                creator.getName(),
                maintenanceTicket.getCreatedDate().format(DATE_TIME_FORMAT),
                maintenanceTicket.getMaintenanceNote()
        ));
        addAttachment(helper, documents);
    }

    private void createTemplate(MaintenanceTicketUpdatedEvent event, MimeMessageHelper helper) throws MessagingException {
        MaintenanceTicket maintenanceTicket = event.getMaintenanceTicket();
        Equipment equipment = maintenanceTicket.getEquipment();
        User creator = maintenanceTicket.getCreator();
        Department department = equipment.getDepartment();
        List<FileStorage> documents = fileStorageService.getAllFilesOfAnEntity(
                maintenanceTicket.getClass().getSimpleName(),
                maintenanceTicket.getId(),
                FileDescription.DOCUMENT
        );
        helper.setFrom(from);
        helper.setTo(creator.getEmail());
        helper.setCc(getCcEmails(NotificationType.MAINTENANCE, creator.getEmail()));
        helper.setSubject(String.format("""
                [Bảo trì] Phiếu bảo trì thiết bị [%s] đã được cập nhật.
                """, equipment.getName()));
        helper.setText(String.format(
                """
                        Phiếu bảo trì thiết bị: [%s] đã được cập nhật. Thiết bị đã được chuyển sang danh sách: [Chờ duyệt bảo trì].
                        Serial: [%s]
                        Model: [%s]
                        Mã thiết bị: [%s]
                        Khoa Phòng ban: [%s]
                        Ngày bảo trì: [%s]
                        Đơn vị thực hiện bảo trì: [%s]
                        Người tạo phiếu bảo trì: [%s]
                        Ngày tạo phiếu bảo trì: [%s]
                        Ghi chú: [%s]
                        """,
                equipment.getName(),
                equipment.getSerial(),
                equipment.getModel(),
                equipment.getHashCode(),
                department.getName(),
                maintenanceTicket.getMaintenanceDate().format(DATE_TIME_FORMAT),
                maintenanceTicket.getMaintenanceCompany().getName(),
                creator.getName(),
                maintenanceTicket.getCreatedDate().format(DATE_TIME_FORMAT),
                maintenanceTicket.getMaintenanceNote()
        ));
        addAttachment(helper, documents);
    }

    private void createTemplate(MaintenanceTicketAcceptedEvent event, MimeMessageHelper helper) throws MessagingException {
        MaintenanceTicket maintenanceTicket = event.getMaintenanceTicket();
        Equipment equipment = maintenanceTicket.getEquipment();
        User creator = maintenanceTicket.getCreator();
        Department department = equipment.getDepartment();
        List<FileStorage> documents = fileStorageService.getAllFilesOfAnEntity(
                maintenanceTicket.getClass().getSimpleName(),
                maintenanceTicket.getId(),
                FileDescription.DOCUMENT
        );
        helper.setFrom(from);
        helper.setTo(creator.getEmail());
        helper.setCc(getCcEmails(NotificationType.MAINTENANCE, creator.getEmail()));
        String title = maintenanceTicket.getStatus().equals(TicketStatus.ACCEPTED) ? "được chấp nhận" : "bị từ chối";
        String subject = maintenanceTicket.getStatus().equals(TicketStatus.ACCEPTED) ? "Chấp nhận" : "Từ chối";
        String status = maintenanceTicket.getStatus().equals(TicketStatus.ACCEPTED) ? "Đang bảo trì" : "Chờ duyệt bảo trì";
        helper.setSubject(String.format("""
                [Bảo trì] [%s] Phiếu bảo trì thiết bị [%s] đã %s.
                """,subject, equipment.getName(), title));
        helper.setText(String.format(
                """
                        Phiếu bảo trì thiết bị: [%s] đã %s. Thiết bị đã được chuyển sang danh sách: [%s].
                        Serial: [%s]
                        Model: [%s]
                        Mã thiết bị: [%s]
                        Khoa Phòng ban: [%s]
                        Ngày bảo trì: [%s]
                        Đơn vị thực hiện bảo trì: [%s]
                        Người tạo phiếu bảo trì: [%s]
                        Ngày tạo phiếu bảo trì: [%s]
                        Ghi chú: [%s]
                        Người phê duyệt: [%s]
                        Ngày phê duyệt: [%s]
                        Ghi chú của người phê duyệt: [%s]
                        """,
                equipment.getName(),
                title,
                status,
                equipment.getSerial(),
                equipment.getModel(),
                equipment.getHashCode(),
                department.getName(),
                maintenanceTicket.getMaintenanceDate().format(DATE_TIME_FORMAT),
                maintenanceTicket.getMaintenanceCompany().getName(),
                creator.getName(),
                maintenanceTicket.getCreatedDate().format(DATE_TIME_FORMAT),
                maintenanceTicket.getMaintenanceNote(),
                maintenanceTicket.getApprover().getName(),
                maintenanceTicket.getApprovalDate(),
                maintenanceTicket.getApproverNote()
        ));
        addAttachment(helper, documents);
    }

    private void createTemplate(InspectionTicketCreatedEvent event, MimeMessageHelper helper) throws MessagingException {
        InspectionTicket inspectionTicket = event.getInspectionTicket();
        Equipment equipment = inspectionTicket.getEquipment();
        Supplier inspectionCompany = inspectionTicket.getInspectionCompany();
        User createdBy = inspectionTicket.getCreator();
        Department department = equipment.getDepartment();
        User creator = inspectionTicket.getCreator();
        List<FileStorage> documents = fileStorageService.getAllFilesOfAnEntity(
                inspectionTicket.getClass().getSimpleName(),
                inspectionTicket.getId(),
                FileDescription.DOCUMENT
        );
        helper.setFrom(from);
        helper.setTo(creator.getEmail());
        helper.setCc(getCcEmails(NotificationType.INSPECTION, creator.getEmail()));
        helper.setSubject(String.format("""
                [Kiểm định] Phiếu kiểm định thiết bị [%s] đã được tạo.
                """, equipment.getName()));
        helper.setText(String.format(
                """
                        Phiếu kiểm định thiết bị: [%s] đã được tạo. Thiết bị đã được chuyển sang danh sách: [Chờ duyệt kiểm định].
                        Serial: [%s]
                        Model: [%s]
                        Mã thiết bị: [%s]
                        Khoa phòng ban: [%s]
                        Ngày kiểm định: [%s]
                        Đơn vị thực hiện kiểm định: [%s]
                        Người tạo phiếu kiểm định: [%s]
                        Ngày tạo phiếu kiểm định: [%s]
                        Ghi chú: [%s]
                        """,
                equipment.getName(),
                equipment.getSerial(),
                equipment.getModel(),
                equipment.getHashCode(),
                department.getName(),
                inspectionTicket.getInspectionDate().format(DATE_TIME_FORMAT),
                inspectionCompany.getName(),
                createdBy.getName(),
                inspectionTicket.getCreatedDate().format(DATE_TIME_FORMAT),
                inspectionTicket.getInspectionNote()
        ));
        addAttachment(helper, documents);
    }

    private void createTemplate(InspectionTicketUpdatedEvent event, MimeMessageHelper helper) throws MessagingException {
        InspectionTicket inspectionTicket = event.getInspectionTicket();
        Equipment equipment = inspectionTicket.getEquipment();
        Supplier inspectionCompany = inspectionTicket.getInspectionCompany();
        User createdBy = inspectionTicket.getCreator();
        Department department = equipment.getDepartment();
        User creator = inspectionTicket.getCreator();
        List<FileStorage> documents = fileStorageService.getAllFilesOfAnEntity(
                inspectionTicket.getClass().getSimpleName(),
                inspectionTicket.getId(),
                FileDescription.DOCUMENT
        );
        helper.setFrom(from);
        helper.setTo(creator.getEmail());
        helper.setCc(getCcEmails(NotificationType.INSPECTION, creator.getEmail()));
        helper.setSubject(String.format("""
                [Kiểm định] Phiếu kiểm định thiết bị [%s] đã được cập nhật.
                """, equipment.getName()));
        helper.setText(String.format(
                """
                        Phiếu kiểm định thiết bị: [%s] đã được cập nhật. Thiết bị đã được chuyển sang danh sách: [Chờ duyệt kiểm định].
                        Serial: [%s]
                        Model: [%s]
                        Mã thiết bị: [%s]
                        Khoa phòng ban: [%s]
                        Ngày kiểm định: [%s]
                        Đơn vị thực hiện kiểm định: [%s]
                        Người tạo phiếu kiểm định: [%s]
                        Ngày tạo phiếu kiểm định: [%s]
                        Ghi chú: [%s]
                        """,
                equipment.getName(),
                equipment.getSerial(),
                equipment.getModel(),
                equipment.getHashCode(),
                department.getName(),
                inspectionTicket.getInspectionDate().format(DATE_TIME_FORMAT),
                inspectionCompany.getName(),
                createdBy.getName(),
                inspectionTicket.getCreatedDate().format(DATE_TIME_FORMAT),
                inspectionTicket.getInspectionNote()
        ));
        addAttachment(helper, documents);
    }
    private void createTemplate(InspectionTicketAcceptedEvent event, MimeMessageHelper helper) throws MessagingException {
        InspectionTicket inspectionTicket = event.getInspectionTicket();
        Equipment equipment = inspectionTicket.getEquipment();
        Supplier inspectionCompany = inspectionTicket.getInspectionCompany();
        User createdBy = inspectionTicket.getCreator();
        Department department = equipment.getDepartment();
        User creator = inspectionTicket.getCreator();
        List<FileStorage> documents = fileStorageService.getAllFilesOfAnEntity(
                inspectionTicket.getClass().getSimpleName(),
                inspectionTicket.getId(),
                FileDescription.DOCUMENT
        );
        helper.setFrom(from);
        helper.setTo(creator.getEmail());
        helper.setCc(getCcEmails(NotificationType.INSPECTION, creator.getEmail()));
        String title = inspectionTicket.getStatus().equals(TicketStatus.ACCEPTED) ? "được chấp nhận" : "bị từ chối";
        String subject = inspectionTicket.getStatus().equals(TicketStatus.ACCEPTED) ? "Chấp nhận" : "Từ chối";
        String status = inspectionTicket.getStatus().equals(TicketStatus.ACCEPTED) ? "Đã kiểm định" : "Chờ kiểm định";
        helper.setSubject(String.format("""
                [Kiểm định] [%s] Phiếu kiểm định thiết bị [%s] đã %s.
                """, subject, equipment.getName(), title));
        helper.setText(String.format(
                """
                        Phiếu kiểm định thiết bị: [%s] đã %s. Thiết bị đã được chuyển sang danh sách: [%s].
                        Serial: [%s]
                        Model: [%s]
                        Mã thiết bị: [%s]
                        Khoa phòng ban: [%s]
                        Ngày kiểm định: [%s]
                        Đơn vị thực hiện kiểm định: [%s]
                        Người tạo phiếu kiểm định: [%s]
                        Ngày tạo phiếu kiểm định: [%s]
                        Ghi chú: [%s]
                        Người phê duyệt: [%s]
                        Ngày phê duyệt: [%s]
                        Ghi chú của người phê duyệt: [%s]
                        """,
                equipment.getName(),
                title,
                status,
                equipment.getSerial(),
                equipment.getModel(),
                equipment.getHashCode(),
                department.getName(),
                inspectionTicket.getInspectionDate().format(DATE_TIME_FORMAT),
                inspectionCompany.getName(),
                createdBy.getName(),
                inspectionTicket.getCreatedDate().format(DATE_TIME_FORMAT),
                inspectionTicket.getInspectionNote(),
                inspectionTicket.getApprover().getName(),
                inspectionTicket.getApprovalDate(),
                inspectionTicket.getApproverNote()
        ));
        addAttachment(helper, documents);
    }
    public static void addAttachment(MimeMessageHelper helper, List<FileStorage> fileStorages) {
        fileStorages.parallelStream().forEach(fileStorage -> {
            try {
                helper.addAttachment(fileStorage.getName() + "." + fileStorage.getExtension(), new ByteArrayResource(fileStorage.getData()));
            } catch (MessagingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }


    public String[] getCcEmails(NotificationType notificationType, String... exceptEmails) {
        List<Long> roleIds = notificationConfigRepository.findByNotificationType(notificationType)
                .stream()
                .map(notificationConfig -> notificationConfig.getRole().getId())
                .toList();
        List<User> users = userRepository.findAllByRole_IdIn(roleIds);
        Set<String> emails = users.stream().map(User::getEmail).collect(Collectors.toSet());
        Arrays.stream(exceptEmails).forEach(emails::remove);
        return emails.toArray(new String[0]);
    }
}
