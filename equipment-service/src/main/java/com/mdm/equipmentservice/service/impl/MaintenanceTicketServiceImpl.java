package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.event.MaintenanceTicketAcceptedEvent;
import com.mdm.equipmentservice.event.MaintenanceTicketCreatedEvent;
import com.mdm.equipmentservice.event.MaintenanceTicketUpdatedEvent;
import com.mdm.equipmentservice.exception.ResourceNotFoundException;
import com.mdm.equipmentservice.mapper.EquipmentMapper;
import com.mdm.equipmentservice.mapper.MaintenanceTicketMapper;
import com.mdm.equipmentservice.model.dto.form.AcceptMaintenanceTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateMaintenanceTicketForm;
import com.mdm.equipmentservice.model.dto.form.UpdateMaintenanceTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.MaintenanceTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListMaintenanceDto;
import com.mdm.equipmentservice.model.dto.list.MaintenanceTicketListDto;
import com.mdm.equipmentservice.model.entity.Equipment;
import com.mdm.equipmentservice.model.entity.EquipmentStatus;
import com.mdm.equipmentservice.model.entity.FileDescription;
import com.mdm.equipmentservice.model.entity.MaintenanceTicket;
import com.mdm.equipmentservice.model.repository.EquipmentRepository;
import com.mdm.equipmentservice.model.repository.MaintenanceRepository;
import com.mdm.equipmentservice.query.param.GetEquipmentsForMaintenanceQueryParam;
import com.mdm.equipmentservice.query.predicate.EquipmentPredicate;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.service.MaintenanceTicketService;
import com.mdm.equipmentservice.util.MessageUtil;
import com.mdm.equipmentservice.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class MaintenanceTicketServiceImpl implements MaintenanceTicketService {
    private final MaintenanceRepository maintenanceRepository;

    private final MessageUtil messageUtil;

    private final FileStorageService fileStorageService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final EquipmentRepository equipmentRepository;

    private final MaintenanceTicketMapper maintenanceTicketMapper;

    private final ValidationUtil validationUtil;

    private final EquipmentMapper equipmentMapper;

    public MaintenanceTicketServiceImpl(
            MaintenanceRepository maintenanceRepository, MessageUtil messageUtil, FileStorageService fileStorageService,
            ApplicationEventPublisher applicationEventPublisher, EquipmentRepository equipmentRepository,
            MaintenanceTicketMapper maintenanceTicketMapper,
            ValidationUtil validationUtil, EquipmentMapper equipmentMapper
    ) {
        this.maintenanceRepository = maintenanceRepository;
        this.messageUtil = messageUtil;
        this.fileStorageService = fileStorageService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.equipmentRepository = equipmentRepository;
        this.maintenanceTicketMapper = maintenanceTicketMapper;
        this.validationUtil = validationUtil;
        this.equipmentMapper = equipmentMapper;
    }


    @Override
    @Transactional
    public MaintenanceTicketFullInfoDto createMaintenanceTicket(
            CreateMaintenanceTicketForm createMaintenanceTicketForm, Long equipmentId, MultipartFile[] attachments
    ) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        MaintenanceTicket maintenanceTicket = maintenanceTicketMapper.createTicket(createMaintenanceTicketForm);
        maintenanceTicket.setEquipment(equipment);
        MaintenanceTicket savedMaintenanceTicket = maintenanceRepository.save(maintenanceTicket);
        equipment.setStatus(EquipmentStatus.PENDING_ACCEPT_MAINTENANCE);
        CollectionUtils.emptyIfNull(equipment.getMaintenanceTickets()).add(savedMaintenanceTicket);
        equipmentRepository.save(equipment);
        fileStorageService.uploadMultipleFiles(
                savedMaintenanceTicket.getClass().getSimpleName(),
                savedMaintenanceTicket.getId(),
                FileDescription.DOCUMENT,
                attachments
        );
        applicationEventPublisher.publishEvent(new MaintenanceTicketCreatedEvent(savedMaintenanceTicket)); //TODO: listen event
        return maintenanceTicketMapper.toFullInfoDto(savedMaintenanceTicket);
    }

    @Override
    public Page<EquipmentListMaintenanceDto> getAllEquipmentsForMaintenance(
            GetEquipmentsForMaintenanceQueryParam getEquipmentsForMaintenanceQueryParam, Pageable pageable
    ) {
        Page<Equipment> equipmentPage = equipmentRepository.findAll(
                EquipmentPredicate.getAllEquipmentsForMaintenancePredicate(getEquipmentsForMaintenanceQueryParam),
                pageable
        );
        return equipmentPage.map(equipmentMapper::toListMaintenanceDto);

    }

    @Override
    public MaintenanceTicketFullInfoDto getMaintenanceDetail(Long equipmentId, Long maintenanceId) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        MaintenanceTicket maintenanceTicket = equipment.getMaintenanceTickets()
                .stream()
                .filter(t -> t.getId().equals(maintenanceId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("maintenanceNotFound")));
        return maintenanceTicketMapper.toFullInfoDto(maintenanceTicket);
    }

    @Override
    @Transactional
    public MaintenanceTicketFullInfoDto acceptMaintenanceTicket(
            Long equipmentId, Long maintenanceId, AcceptMaintenanceTicketForm acceptMaintenanceTicketForm
    ) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        MaintenanceTicket maintenanceTicket = equipment.getMaintenanceTickets()
                .stream()
                .filter(t -> t.getId().equals(maintenanceId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("maintenanceNotFound")));
        maintenanceTicket = maintenanceTicketMapper.acceptTicket(acceptMaintenanceTicketForm, maintenanceTicket);
        validationUtil.validateEquipmentStatus(
                equipment,
                EquipmentStatus.PENDING_ACCEPT_MAINTENANCE,
                "cannotAcceptMaintenanceTicketOnEquipmentNotPendingAcceptMaintenance"
        );
        if (acceptMaintenanceTicketForm.getIsApproved()) {
            equipment.setStatus(EquipmentStatus.UNDER_MAINTENANCE);
        } else {
            equipment.setStatus(EquipmentStatus.IN_USE);
        }
        equipmentRepository.save(equipment);
        applicationEventPublisher.publishEvent(new MaintenanceTicketAcceptedEvent(maintenanceTicket));//TODO: listen event
        return maintenanceTicketMapper.toFullInfoDto(maintenanceTicket);
    }


    @Override
    public Page<MaintenanceTicketListDto> getAllMaintenanceTicketsOfAnEquipment(Long equipmentId, Pageable pageable) {
        Page<MaintenanceTicket> maintenances = maintenanceRepository.findByEquipment_Id(equipmentId, pageable);
        return maintenances.map(maintenanceTicketMapper::toListDto);
    }

    @Override
    public MaintenanceTicketFullInfoDto updateMaintenanceTicket(
            Long equipmentId, Long maintenanceId, UpdateMaintenanceTicketForm acceptMaintenanceTicketForm, MultipartFile[] attachments
    ) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        MaintenanceTicket maintenanceTicket = equipment.getMaintenanceTickets()
                .stream()
                .filter(t -> t.getId().equals(maintenanceId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("maintenanceNotFound")));
        validationUtil.validateEquipmentStatus(equipment, EquipmentStatus.UNDER_MAINTENANCE, "cannotUpdateMaintenanceTicketOnNotUnderMaintenanceEquipment");
        maintenanceTicket = maintenanceTicketMapper.updateTicket(acceptMaintenanceTicketForm, maintenanceTicket);
        equipment.setStatus(EquipmentStatus.IN_USE);
        equipment.setLastMaintenanceDate(maintenanceTicket.getMaintenanceDate());
        equipmentRepository.save(equipment);
        fileStorageService.uploadMultipleFiles(
                maintenanceTicket.getClass().getSimpleName(),
                maintenanceTicket.getId(),
                FileDescription.DOCUMENT,
                attachments
        );
        applicationEventPublisher.publishEvent(new MaintenanceTicketUpdatedEvent(maintenanceTicket));//TODO: listen event
        return maintenanceTicketMapper.toFullInfoDto(maintenanceTicket);
    }
}
