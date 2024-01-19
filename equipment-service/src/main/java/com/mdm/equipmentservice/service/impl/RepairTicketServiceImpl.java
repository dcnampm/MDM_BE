package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.event.RepairTicketAcceptanceTestingEvent;
import com.mdm.equipmentservice.event.RepairTicketAcceptedEvent;
import com.mdm.equipmentservice.event.RepairTicketCreatedEvent;
import com.mdm.equipmentservice.event.RepairTicketUpdatedEvent;
import com.mdm.equipmentservice.exception.ResourceNotFoundException;
import com.mdm.equipmentservice.mapper.EquipmentMapper;
import com.mdm.equipmentservice.mapper.RepairTicketMapper;
import com.mdm.equipmentservice.model.dto.form.AcceptRepairTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateRepairTicketForm;
import com.mdm.equipmentservice.model.dto.form.UpdateRepairTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.RepairTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListRepairDto;
import com.mdm.equipmentservice.model.entity.*;
import com.mdm.equipmentservice.model.repository.EquipmentRepository;
import com.mdm.equipmentservice.model.repository.RepairTicketRepository;
import com.mdm.equipmentservice.model.repository.UserRepository;
import com.mdm.equipmentservice.query.param.GetEquipmentsForRepairQueryParam;
import com.mdm.equipmentservice.query.predicate.EquipmentPredicate;
import com.mdm.equipmentservice.service.EquipmentService;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.service.RepairTicketService;
import com.mdm.equipmentservice.util.*;
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
public class RepairTicketServiceImpl implements RepairTicketService {

    private final UserRepository userRepository;

    private final RepairTicketRepository repairTicketRepository;

    private final MessageUtil messageUtil;

    private final EquipmentService equipmentService;

    private final EquipmentRepository equipmentRepository;

    private final EquipmentMapper equipmentMapper;

    private final RepairTicketMapper repairTicketMapper;

    private final FileStorageService fileStorageService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final ValidationUtil validationUtil;

    @Autowired
    public RepairTicketServiceImpl(
            MessageUtil messageUtil, EquipmentService equipmentService, EquipmentRepository equipmentRepository,
            EquipmentMapper equipmentMapper,
            RepairTicketMapper repairTicketMapper,
            RepairTicketRepository repairTicketRepository,
            FileStorageService fileStorageService,
            ApplicationEventPublisher applicationEventPublisher,
            UserRepository userRepository,
            ValidationUtil validationUtil
    ) {

        this.messageUtil = messageUtil;
        this.equipmentService = equipmentService;
        this.equipmentRepository = equipmentRepository;
        this.equipmentMapper = equipmentMapper;
        this.repairTicketMapper = repairTicketMapper;
        this.repairTicketRepository = repairTicketRepository;
        this.fileStorageService = fileStorageService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.userRepository = userRepository;
        this.validationUtil = validationUtil;
    }
/*
    @Override
    public Page<EquipmentListRepairDto> getAllEquipmentForRepairing(GetEquipmentsQueryParam getEquipmentsQueryParam, Pageable pageable) {
        log.info("Get all equipments for repairing");
        Page<Equipment> equipmentPage = equipmentRepository.findAll(
                EquipmentPredicate.getAllEquipmentsForRepairingPredicate(getEquipmentsQueryParam),
                pageable
        );
        return equipmentPage.map(equipmentMapper::toListRepairDto);
    }

    @Override
    @Transactional
    public void createRepairTicket(Long equipmentId, CreateRepairTicketForm createRepairTicketForm, MultipartFile[] attachments) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        validationUtil.validateEquipmentStatus(equipment, EquipmentStatus.BROKEN, "cannotCreateRepairTicketOnNotBrokenEquipment");
        RepairTicket repairTicket = repairTicketMapper.createTicket(createRepairTicketForm, equipmentId);
        equipment.setStatus(EquipmentStatus.PENDING_ACCEPT_REPAIR);
        CollectionUtils.emptyIfNull(equipment.getRepairTickets()).add(repairTicket);
        repairTicket = repairTicketRepository.save(repairTicket);
        equipmentRepository.save(equipment);
        fileStorageService.uploadMultipleFiles(repairTicket.getClass().getSimpleName(), repairTicket.getId(), FileDescription.DOCUMENT, attachments);
        log.info("Repair ticket created successfully : {}", repairTicket);
        applicationEventPublisher.publishEvent(new RepairTicketCreatedEvent(repairTicket));
    }

    @Override
    public RepairTicketFullInfoDto getRepairTicketDetailByEquipmentId(Long equipmentId) {
        List<RepairTicket> repairTickets = repairTicketRepository.findByEquipment_IdOrderByIdDesc(equipmentId);
        if (CollectionUtils.isEmpty(repairTickets)) {
            throw new ResourceNotFoundException(messageUtil.getMessage("repairTicketNotFound"));
        }
        RepairTicket repairTicket = repairTickets.get(0);
        return repairTicketMapper.toFullInfoDto(repairTicket);
    }

    @Override
    public RepairTicketFullInfoDto acceptRepairTicket(Long repairTicketId, AcceptRepairTicketForm acceptRepairTicketForm) {
        RepairTicket repairTicket = repairTicketRepository.findById(repairTicketId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("repairTicketNotFound")));
        repairTicket = repairTicketMapper.acceptTicket(acceptRepairTicketForm, repairTicket);
        Equipment equipment = repairTicket.getEquipment();
        validationUtil.validateEquipmentStatus(equipment, EquipmentStatus.PENDING_ACCEPT_REPAIR, "cannotAcceptRepairTicketOnNotPendingAcceptRepairEquipment");
        equipment.setStatus(acceptRepairTicketForm.getIsAccepted() ? EquipmentStatus.REPAIRING : EquipmentStatus.BROKEN);
        equipmentRepository.save(equipment);
        repairTicket = repairTicketRepository.save(repairTicket);
        applicationEventPublisher.publishEvent(new RepairTicketAcceptedEvent(repairTicket));
        return repairTicketMapper.toFullInfoDto(repairTicket);
    }
*/


    @Override
    @Transactional
    public RepairTicketFullInfoDto createRepairTicket(
            CreateRepairTicketForm createRepairTicketForm, Long equipmentId, MultipartFile[] attachments
    ) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        validationUtil.validateEquipmentStatus(equipment, EquipmentStatus.BROKEN, messageUtil.getMessage("cannotCreateRepairTicketOnNotBrokenEquipment"));
        RepairTicket repairTicket = repairTicketMapper.createTicket(createRepairTicketForm, equipmentId);
        equipment = equipmentMapper.createRepairTicket(equipment, repairTicket, createRepairTicketForm);
        equipment = equipmentRepository.save(equipment);
        repairTicket = CollectionUtil.getLastElement(equipment.getRepairTickets());
        fileStorageService.uploadMultipleFiles(
                repairTicket.getClass().getSimpleName(),
                repairTicket.getId(),
                FileDescription.DOCUMENT,
                attachments
        );
        applicationEventPublisher.publishEvent(new RepairTicketCreatedEvent(repairTicket));
        return repairTicketMapper.toFullInfoDto(repairTicket);
    }

    @Override
    public Page<EquipmentListRepairDto> getAllEquipmentsForRepair(
            GetEquipmentsForRepairQueryParam getEquipmentsForRepairQueryParam, Pageable pageable
    ) {
        Page<Equipment> equipmentPage = equipmentRepository.findAll(
                EquipmentPredicate.getAllEquipmentsForRepairPredicate(getEquipmentsForRepairQueryParam),
                pageable
        );
        return equipmentPage.map(equipmentMapper::toListRepairDto);
    }

    @Override
    public RepairTicketFullInfoDto getRepairDetail(Long equipmentId, Long repairId) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        RepairTicket repairTicket = CollectionUtil.getLastElement(equipment.getRepairTickets());
        if (repairTicket == null) {
            throw new ResourceNotFoundException(messageUtil.getMessage("repairNotFound"));
        }
        return repairTicketMapper.toFullInfoDto(repairTicket);
    }

    @Override
    @Transactional
    public RepairTicketFullInfoDto acceptRepairTicket(
            Long equipmentId, Long repairId, AcceptRepairTicketForm acceptRepairTicketForm
    ) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        RepairTicket repairTicket = CollectionUtil.getLastElement(equipment.getRepairTickets());
        if (repairTicket == null) {
            throw new ResourceNotFoundException(messageUtil.getMessage("repairNotFound"));
        }
        repairTicketMapper.acceptTicket(acceptRepairTicketForm, repairTicket);
        validationUtil.validateEquipmentStatus(
                equipment,
                EquipmentStatus.PENDING_ACCEPT_REPAIR,
                "cannotAcceptRepairTicketOnEquipmentNotPendingAcceptRepair"
        );
        equipment = equipmentMapper.acceptRepairTicket(equipment, acceptRepairTicketForm);
        equipment = equipmentRepository.save(equipment);
        repairTicket = CollectionUtil.getLastElement(equipment.getRepairTickets());
        applicationEventPublisher.publishEvent(new RepairTicketAcceptedEvent(repairTicket));
        return repairTicketMapper.toFullInfoDto(repairTicket);
    }

    @Override
    public RepairTicketFullInfoDto updateRepairTicket(Long repairTicketId, Long equipmentId, UpdateRepairTicketForm updateRepairTicketForm, MultipartFile[] attachments) {
        RepairTicket repairTicket = repairTicketRepository.findById(repairTicketId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("repairTicketNotFound")));
        validationUtil.validateRepairStatus(repairTicket, RepairStatus.IN_PROGRESS, "cannotUpdateRepairTicketOnNotInProgressRepairTicket");
        repairTicket = repairTicketMapper.updateTicket(updateRepairTicketForm, repairTicket);
        Equipment equipment = repairTicket.getEquipment();
        equipment.setStatus(EquipmentStatus.PENDING_ACCEPTANCE_TESTING);
        equipmentRepository.save(equipment);
        fileStorageService.uploadMultipleFiles(repairTicket.getClass().getSimpleName(), repairTicket.getId(), FileDescription.DOCUMENT, attachments);
        applicationEventPublisher.publishEvent(new RepairTicketUpdatedEvent(repairTicket));
        return repairTicketMapper.toFullInfoDto(repairTicket);
    }

    /**
     * Nghiệm thu thiết bị
     */
    @Override
    @Transactional
    public void acceptanceTesting(Long repairTicketId, Long ticketId, MultipartFile[] attachments) {
        RepairTicket repairTicket = repairTicketRepository.findById(repairTicketId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("repairTicketNotFound")));
        //if repair status is done, it means that equipment is repaired successfully, then set equipment status to IN_USE
        //else set equipment status to INACTIVE
        Equipment equipment = repairTicket.getEquipment();
        validationUtil.validateEquipmentStatus(
                equipment,
                EquipmentStatus.PENDING_ACCEPTANCE_TESTING,
                "cannotAcceptanceTestingOnNotPendingAcceptanceTestingEquipment"
        );
        equipment.setStatus(repairTicket.getRepairStatus().equals(RepairStatus.DONE) ? EquipmentStatus.IN_USE : EquipmentStatus.INACTIVE);
        equipmentRepository.save(equipment);
        repairTicket.setAcceptanceTester(SecurityUtil.getCurrentLoggedInUser(userRepository));
        repairTicket = repairTicketRepository.saveAndFlush(repairTicket);
        fileStorageService.uploadMultipleFiles(repairTicket.getClass().getSimpleName(), repairTicket.getId(), FileDescription.DOCUMENT, attachments);
        applicationEventPublisher.publishEvent(new RepairTicketAcceptanceTestingEvent(repairTicket));
    }
}
