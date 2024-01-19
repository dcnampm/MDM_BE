package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.event.ReportBrokenTicketAcceptedEvent;
import com.mdm.equipmentservice.event.ReportBrokenTicketCreatedEvent;
import com.mdm.equipmentservice.exception.ResourceNotFoundException;
import com.mdm.equipmentservice.mapper.EquipmentMapper;
import com.mdm.equipmentservice.mapper.ReportBrokenTicketMapper;
import com.mdm.equipmentservice.model.dto.form.AcceptReportBrokenTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateReportBrokenTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.ReportBrokenTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListReportBrokenDto;
import com.mdm.equipmentservice.model.entity.Equipment;
import com.mdm.equipmentservice.model.entity.EquipmentStatus;
import com.mdm.equipmentservice.model.entity.FileDescription;
import com.mdm.equipmentservice.model.entity.ReportBrokenTicket;
import com.mdm.equipmentservice.model.repository.EquipmentRepository;
import com.mdm.equipmentservice.model.repository.ReportBrokenTicketRepository;
import com.mdm.equipmentservice.query.param.GetEquipmentsForReportBrokenQueryParam;
import com.mdm.equipmentservice.query.predicate.EquipmentPredicate;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.service.ReportBrokenTicketService;
import com.mdm.equipmentservice.util.CollectionUtil;
import com.mdm.equipmentservice.util.CommonUtil;
import com.mdm.equipmentservice.util.MessageUtil;
import com.mdm.equipmentservice.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ReportBrokenTicketServiceImpl implements ReportBrokenTicketService {

    private final EquipmentRepository equipmentRepository;

    private final ReportBrokenTicketRepository reportBrokenTicketRepository;

    private final ReportBrokenTicketMapper reportBrokenTicketMapper;

    private final FileStorageService fileStorageService;

    private final EquipmentMapper equipmentMapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final MessageUtil messageUtil;

    private final ValidationUtil validationUtil;

    @Autowired
    public ReportBrokenTicketServiceImpl(ReportBrokenTicketMapper reportBrokenTicketMapper, FileStorageService fileStorageService,
                                         ReportBrokenTicketRepository reportBrokenTicketRepository, EquipmentRepository equipmentRepository,
                                         EquipmentMapper equipmentMapper, ApplicationEventPublisher applicationEventPublisher, MessageUtil messageUtil,
                                         ValidationUtil validationUtil) {
        this.reportBrokenTicketMapper = reportBrokenTicketMapper;
        this.fileStorageService = fileStorageService;
        this.reportBrokenTicketRepository = reportBrokenTicketRepository;
        this.equipmentRepository = equipmentRepository;
        this.equipmentMapper = equipmentMapper;
        this.applicationEventPublisher = applicationEventPublisher;
        this.messageUtil = messageUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    @Transactional
    public ReportBrokenTicketFullInfoDto createReportBrokenTicket(CreateReportBrokenTicketForm createReportBrokenTicketForm, Long equipmentId,
                                                                  MultipartFile[] attachments) {
        Equipment equipment =
                equipmentRepository.findById(equipmentId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        validationUtil.validateEquipmentStatus(equipment, EquipmentStatus.IN_USE, messageUtil.getMessage("cannotReportBrokenOnEquipmentNotInUse"));
        ReportBrokenTicket reportBrokenTicket = reportBrokenTicketMapper.createTicket(createReportBrokenTicketForm, equipmentId);
        equipment = equipmentMapper.createReportBrokenTicket(equipment, reportBrokenTicket, createReportBrokenTicketForm);
        equipment = equipmentRepository.save(equipment);
        reportBrokenTicket = CollectionUtil.getLastElement(equipment.getReportBrokenTickets());
        fileStorageService.uploadMultipleFiles(
                reportBrokenTicket.getClass().getSimpleName(),
                reportBrokenTicket.getId(),
                FileDescription.DOCUMENT,
                attachments
        );
        applicationEventPublisher.publishEvent(new ReportBrokenTicketCreatedEvent(reportBrokenTicket));
        return reportBrokenTicketMapper.toFullInfoDto(reportBrokenTicket);
    }

    @Override
    public Page<EquipmentListReportBrokenDto> getAllEquipmentsForReportBroken(GetEquipmentsForReportBrokenQueryParam getEquipmentsForReportBrokenQueryParam,
                                                                              Pageable pageable) {
        Page<Equipment> equipmentPage =
                equipmentRepository.findAll(EquipmentPredicate.getAllEquipmentsForReportBrokenPredicate(getEquipmentsForReportBrokenQueryParam), pageable);
        return equipmentPage.map(equipmentMapper::toListReportBrokenDto);
    }

    @Override
    public ReportBrokenTicketFullInfoDto getReportBrokenDetail(Long equipmentId, Long reportBrokenId) {
        Equipment equipment =
                equipmentRepository.findById(equipmentId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        ReportBrokenTicket reportBrokenTicket = CollectionUtil.getLastElement(equipment.getReportBrokenTickets());
        if (reportBrokenTicket == null) {
            throw new ResourceNotFoundException(messageUtil.getMessage("reportBrokenNotFound"));
        }
        return reportBrokenTicketMapper.toFullInfoDto(reportBrokenTicket);
    }

    @Override
    @Transactional
    public ReportBrokenTicketFullInfoDto acceptReportBrokenTicket(Long equipmentId, Long reportBrokenId,
                                                                  AcceptReportBrokenTicketForm acceptReportBrokenTicketForm) {
        Equipment equipment =
                equipmentRepository.findById(equipmentId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        ReportBrokenTicket reportBrokenTicket = CollectionUtil.getLastElement(equipment.getReportBrokenTickets());
        if (reportBrokenTicket == null) {
            throw new ResourceNotFoundException(messageUtil.getMessage("reportBrokenNotFound"));
        }
        reportBrokenTicketMapper.acceptTicket(acceptReportBrokenTicketForm, reportBrokenTicket);
        validationUtil.validateEquipmentStatus(
                equipment,
                EquipmentStatus.PENDING_REPORT_BROKEN,
                "cannotAcceptReportBrokenTicketOnEquipmentNotPendingReportBroken"
        );
        equipment = equipmentMapper.acceptReportBrokenTicket(equipment, acceptReportBrokenTicketForm);
        equipment = equipmentRepository.save(equipment);
        reportBrokenTicket = CollectionUtil.getLastElement(equipment.getReportBrokenTickets());
        applicationEventPublisher.publishEvent(new ReportBrokenTicketAcceptedEvent(reportBrokenTicket));
        return reportBrokenTicketMapper.toFullInfoDto(reportBrokenTicket);
    }
}
