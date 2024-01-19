package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.event.LiquidationTicketAcceptedEvent;
import com.mdm.equipmentservice.event.LiquidationTicketCreatedEvent;
import com.mdm.equipmentservice.exception.ResourceNotFoundException;
import com.mdm.equipmentservice.mapper.EquipmentMapper;
import com.mdm.equipmentservice.mapper.LiquidationTicketMapper;
import com.mdm.equipmentservice.model.dto.form.AcceptLiquidationTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateLiquidationTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.LiquidationTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListLiquidationDto;
import com.mdm.equipmentservice.model.entity.Equipment;
import com.mdm.equipmentservice.model.entity.EquipmentStatus;
import com.mdm.equipmentservice.model.entity.FileDescription;
import com.mdm.equipmentservice.model.entity.LiquidationTicket;
import com.mdm.equipmentservice.model.repository.EquipmentRepository;
import com.mdm.equipmentservice.model.repository.LiquidationTicketRepository;
import com.mdm.equipmentservice.query.param.GetEquipmentsForLiquidationQueryParam;
import com.mdm.equipmentservice.query.predicate.EquipmentPredicate;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.service.LiquidationTicketService;
import com.mdm.equipmentservice.util.MessageUtil;
import com.mdm.equipmentservice.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class LiquidationTicketServiceImpl implements LiquidationTicketService {
    private final LiquidationTicketRepository liquidationTicketRepository;

    private final MessageUtil messageUtil;

    private final EquipmentRepository equipmentRepository;
    private final EquipmentMapper equipmentMapper;

    private final FileStorageService fileStorageService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final LiquidationTicketMapper liquidationTicketMapper;
    private final ValidationUtil validationUtil;

    public LiquidationTicketServiceImpl(MessageUtil messageUtil, EquipmentRepository equipmentRepository,
                                        FileStorageService fileStorageService, ApplicationEventPublisher applicationEventPublisher,
                                        LiquidationTicketMapper liquidationTicketMapper, LiquidationTicketRepository liquidationTicketRepository,
                                        EquipmentMapper equipmentMapper, ValidationUtil validationUtil) {
        this.messageUtil = messageUtil;
        this.equipmentRepository = equipmentRepository;
        this.fileStorageService = fileStorageService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.liquidationTicketMapper = liquidationTicketMapper;
        this.liquidationTicketRepository = liquidationTicketRepository;
        this.equipmentMapper = equipmentMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Page<EquipmentListLiquidationDto> getAllEquipmentsForLiquidation(GetEquipmentsForLiquidationQueryParam getEquipmentsForLiquidationQueryParam,
                                                                            Pageable pageable) {
        Page<Equipment> equipmentPage =
                equipmentRepository.findAll(EquipmentPredicate.getAllEquipmentsForLiquidationPredicate(getEquipmentsForLiquidationQueryParam), pageable);
        return equipmentPage.map(equipmentMapper::toListLiquidationDto);
    }

    @Override
    public LiquidationTicketFullInfoDto createLiquidationTicket(CreateLiquidationTicketForm createLiquidationTicketForm, Long equipmentId,
                                                                MultipartFile[] attachments) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        validationUtil.validateEquipmentStatus(equipment, EquipmentStatus.INACTIVE, "cannotCreateLiquidationTicketOnEquipmentNotInactive");
        LiquidationTicket liquidationTicket = liquidationTicketMapper.createTicket(createLiquidationTicketForm);
        equipment = equipmentMapper.addLiquidationTicket(equipment, liquidationTicket);
        liquidationTicket.setEquipment(equipment);
        LiquidationTicket savedLiquidationTicketTicket = liquidationTicketRepository.save(liquidationTicket);
        fileStorageService.uploadMultipleFiles(
                savedLiquidationTicketTicket.getClass().getSimpleName(),
                savedLiquidationTicketTicket.getId(),
                FileDescription.DOCUMENT,
                attachments
        );
        applicationEventPublisher.publishEvent(new LiquidationTicketCreatedEvent(savedLiquidationTicketTicket)); //TODO: listen event
        return liquidationTicketMapper.toFullInfoDto(savedLiquidationTicketTicket);
    }


    @Override
    public LiquidationTicketFullInfoDto getLiquidationDetail(Long equipmentId, Long liquidationId) {
        Equipment equipment =
                equipmentRepository.findById(equipmentId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        LiquidationTicket liquidationTicket = equipment.getLiquidationTickets().stream()
                .filter(lt -> lt.getId().equals(liquidationId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("liquidationNotFound")));
        return liquidationTicketMapper.toFullInfoDto(liquidationTicket);
    }

    @Override
    @Transactional
    public LiquidationTicketFullInfoDto acceptLiquidationTicket(Long equipmentId, Long liquidationId, AcceptLiquidationTicketForm acceptLiquidationTicketForm) {
        Equipment equipment =
                equipmentRepository.findById(equipmentId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        LiquidationTicket liquidationTicket = equipment.getLiquidationTickets().stream()
                .filter(lt -> lt.getId().equals(liquidationId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("liquidationNotFound")));
        liquidationTicket = liquidationTicketMapper.acceptTicket(acceptLiquidationTicketForm, liquidationTicket);
        validationUtil.validateEquipmentStatus(
                equipment,
                EquipmentStatus.PENDING_ACCEPT_LIQUIDATION,
                "cannotAcceptLiquidationTicketOnEquipmentNotPendingAcceptLiquidation"
        );
        if (acceptLiquidationTicketForm.getIsApproved()) {
            equipment.setStatus(EquipmentStatus.LIQUIDATED);
        } else {
            equipment.setStatus(EquipmentStatus.INACTIVE);
        }
        equipmentRepository.save(equipment);
        applicationEventPublisher.publishEvent(new LiquidationTicketAcceptedEvent(liquidationTicket));//TODO: listen event
        return liquidationTicketMapper.toFullInfoDto(liquidationTicket);
    }
}
