package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.event.InspectionTicketAcceptedEvent;
import com.mdm.equipmentservice.event.InspectionTicketCreatedEvent;
import com.mdm.equipmentservice.event.InspectionTicketUpdatedEvent;
import com.mdm.equipmentservice.exception.ResourceNotFoundException;
import com.mdm.equipmentservice.mapper.EquipmentMapper;
import com.mdm.equipmentservice.mapper.InspectionTicketMapper;
import com.mdm.equipmentservice.model.dto.form.AcceptInspectionTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateInspectionTicketForm;
import com.mdm.equipmentservice.model.dto.form.UpdateInspectionTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.InspectionTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListInspectionDto;
import com.mdm.equipmentservice.model.dto.list.InspectionTicketListDto;
import com.mdm.equipmentservice.model.entity.*;
import com.mdm.equipmentservice.model.repository.EquipmentRepository;
import com.mdm.equipmentservice.model.repository.InspectionRepository;
import com.mdm.equipmentservice.query.param.GetEquipmentsForInspectionQueryParam;
import com.mdm.equipmentservice.query.predicate.EquipmentPredicate;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.service.InspectionTicketService;
import com.mdm.equipmentservice.util.MessageUtil;
import com.mdm.equipmentservice.util.ValidationUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class InspectionTicketServiceImpl implements InspectionTicketService {
    private final InspectionRepository inspectionRepository;

    private final MessageUtil messageUtil;

    private final FileStorageService fileStorageService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final EquipmentRepository equipmentRepository;

    private final InspectionTicketMapper inspectionTicketMapper;

    private final ValidationUtil validationUtil;

    private final EquipmentMapper equipmentMapper;

    public InspectionTicketServiceImpl(
            InspectionRepository inspectionRepository, MessageUtil messageUtil, FileStorageService fileStorageService,
            ApplicationEventPublisher applicationEventPublisher, EquipmentRepository equipmentRepository, InspectionTicketMapper inspectionTicketMapper,
            ValidationUtil validationUtil, EquipmentMapper equipmentMapper
    ) {
        this.inspectionRepository = inspectionRepository;
        this.messageUtil = messageUtil;
        this.fileStorageService = fileStorageService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.equipmentRepository = equipmentRepository;
        this.inspectionTicketMapper = inspectionTicketMapper;
        this.validationUtil = validationUtil;
        this.equipmentMapper = equipmentMapper;
    }

    @Override
    public Page<EquipmentListInspectionDto> getAllEquipmentsForInspection(
            GetEquipmentsForInspectionQueryParam getEquipmentsForInspectionQueryParam, Pageable pageable
    ) {
        Page<Equipment> equipmentPage = equipmentRepository.findAll(EquipmentPredicate.getAllEquipmentsForInspectionPredicate(
                getEquipmentsForInspectionQueryParam), pageable);
        return equipmentPage.map(equipmentMapper::toListInspectionDto);
    }

    @Override
    public InspectionTicketFullInfoDto createInspectionTicket(
            CreateInspectionTicketForm createInspectionTicketForm, Long equipmentId, MultipartFile[] attachments
    ) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        InspectionTicket inspectionTicket = inspectionTicketMapper.createTicket(createInspectionTicketForm);
        inspectionTicket.setEquipment(equipment);
        InspectionTicket savedInspectionTicketTicket = inspectionRepository.save(inspectionTicket);
        equipment.setStatus(EquipmentStatus.PENDING_ACCEPT_INSPECTION);
        CollectionUtils.emptyIfNull(equipment.getInspectionTickets()).add(savedInspectionTicketTicket);
        equipmentRepository.save(equipment);
        fileStorageService.uploadMultipleFiles(
                savedInspectionTicketTicket.getClass().getSimpleName(),
                savedInspectionTicketTicket.getId(),
                FileDescription.DOCUMENT,
                attachments
        );
        applicationEventPublisher.publishEvent(new InspectionTicketCreatedEvent(savedInspectionTicketTicket)); //TODO: listen event
        return inspectionTicketMapper.toFullInfoDto(savedInspectionTicketTicket);
    }

    @Override
    public InspectionTicketFullInfoDto getInspectionDetail(Long equipmentId, Long inspectionId) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        InspectionTicket inspectionTicket = equipment.getInspectionTickets()
                .stream()
                .filter(t -> t.getId().equals(inspectionId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("inspectionNotFound")));
        return inspectionTicketMapper.toFullInfoDto(inspectionTicket);
    }

    @Override
    @Transactional
    public InspectionTicketFullInfoDto acceptInspectionTicket(
            Long equipmentId, Long inspectionId, AcceptInspectionTicketForm acceptInspectionTicketForm
    ) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        InspectionTicket inspectionTicket = equipment.getInspectionTickets()
                .stream()
                .filter(t -> t.getId().equals(inspectionId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("inspectionNotFound")));
        inspectionTicket = inspectionTicketMapper.acceptTicket(acceptInspectionTicketForm, inspectionTicket);
        validationUtil.validateEquipmentStatus(
                equipment,
                EquipmentStatus.PENDING_ACCEPT_INSPECTION,
                "cannotAcceptInspectionTicketOnEquipmentNotPendingAcceptInspection"
        );
        if (acceptInspectionTicketForm.getIsApproved()) {
            equipment.setStatus(EquipmentStatus.UNDER_INSPECTION);
        } else {
            equipment.setStatus(EquipmentStatus.IN_USE);
        }
        equipmentRepository.save(equipment);
        applicationEventPublisher.publishEvent(new InspectionTicketAcceptedEvent(inspectionTicket));//TODO: listen event
        return inspectionTicketMapper.toFullInfoDto(inspectionTicket);
    }


    @Override
    public Page<InspectionTicketListDto> getAllInspectionTicketsOfAnEquipment(Long equipmentId, Pageable pageable) {
        Page<InspectionTicket> inspections = inspectionRepository.findByEquipment_Id(equipmentId, pageable);
        return inspections.map(inspectionTicketMapper::toListDto);
    }

    @Override
    public InspectionTicketFullInfoDto updateInspectionTicket(
            Long equipmentId, Long inspectionId, UpdateInspectionTicketForm acceptInspectionTicketForm, MultipartFile[] attachments
    ) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        InspectionTicket inspectionTicket = equipment.getInspectionTickets()
                .stream()
                .filter(t -> t.getId().equals(inspectionId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("inspectionNotFound")));
        validationUtil.validateEquipmentStatus(equipment, EquipmentStatus.UNDER_INSPECTION, "cannotUpdateInspectionTicketOnNotUnderInspectionEquipment");
        inspectionTicket = inspectionTicketMapper.updateTicket(acceptInspectionTicketForm, inspectionTicket);
        updateEquipmentStatusAfterUpdateInspectionTicket(equipment, inspectionTicket.getEvaluationStatus());
        equipment.setLastInspectionDate(inspectionTicket.getInspectionDate());
        equipmentRepository.save(equipment);
        fileStorageService.uploadMultipleFiles(inspectionTicket.getClass().getSimpleName(), inspectionTicket.getId(), FileDescription.DOCUMENT, attachments);
        applicationEventPublisher.publishEvent(new InspectionTicketUpdatedEvent(inspectionTicket));//TODO: listen event
        return inspectionTicketMapper.toFullInfoDto(inspectionTicket);
    }

    private void updateEquipmentStatusAfterUpdateInspectionTicket(Equipment equipment, InspectionEvaluationStatus evaluationStatus) {
        switch (evaluationStatus) {
            case GOOD -> equipment.setStatus(EquipmentStatus.IN_USE);
            case BAD_CAN_REPAIR -> equipment.setStatus(EquipmentStatus.BROKEN);
            case BAD_CANNOT_REPAIR -> equipment.setStatus(EquipmentStatus.INACTIVE);
            default -> {
            }
        }
    }

}
