package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.event.HandoverTicketAcceptedEvent;
import com.mdm.equipmentservice.event.HandoverTicketCreatedEvent;
import com.mdm.equipmentservice.exception.ResourceNotFoundException;
import com.mdm.equipmentservice.mapper.EquipmentMapper;
import com.mdm.equipmentservice.mapper.HandoverTicketMapper;
import com.mdm.equipmentservice.model.dto.form.AcceptHandoverTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateHandoverTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.HandoverTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListHandoverDto;
import com.mdm.equipmentservice.model.entity.Equipment;
import com.mdm.equipmentservice.model.entity.EquipmentStatus;
import com.mdm.equipmentservice.model.entity.FileDescription;
import com.mdm.equipmentservice.model.entity.HandoverTicket;
import com.mdm.equipmentservice.model.repository.EquipmentRepository;
import com.mdm.equipmentservice.model.repository.HandoverTicketRepository;
import com.mdm.equipmentservice.model.repository.UserRepository;
import com.mdm.equipmentservice.query.param.GetEquipmentsForHandoverQueryParam;
import com.mdm.equipmentservice.query.predicate.EquipmentPredicate;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.service.HandoverTicketService;
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
public class HandoverTicketServiceImpl implements HandoverTicketService {

    private final EquipmentRepository equipmentRepository;

    private final FileStorageService fileStorageService;

    private final UserRepository userRepository;

    private final HandoverTicketRepository handoverTicketRepository;

    private final MessageUtil messageUtil;

    private final HandoverTicketMapper handoverTicketMapper;

    private final EquipmentMapper equipmentMapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final ValidationUtil validationUtil;

    @Autowired
    public HandoverTicketServiceImpl(
            HandoverTicketMapper handoverTicketMapper,
            HandoverTicketRepository handoverTicketRepository,
            MessageUtil messageUtil,
            UserRepository userRepository,
            EquipmentRepository equipmentRepository,
            FileStorageService fileStorageService,
            EquipmentMapper equipmentMapper,
            ApplicationEventPublisher applicationEventPublisher,
            ValidationUtil validationUtil) {
        this.handoverTicketMapper = handoverTicketMapper;
        this.handoverTicketRepository = handoverTicketRepository;
        this.messageUtil = messageUtil;
        this.userRepository = userRepository;
        this.equipmentRepository = equipmentRepository;
        this.fileStorageService = fileStorageService;
        this.equipmentMapper = equipmentMapper;
        this.applicationEventPublisher = applicationEventPublisher;
        this.validationUtil = validationUtil;
    }

    @Override
    @Transactional
    public HandoverTicketFullInfoDto createHandoverTicket(
            CreateHandoverTicketForm createHandoverTicketForm, Long equipmentId, MultipartFile[] attachments
    ) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        validationUtil.validateEquipmentStatus(equipment, EquipmentStatus.NEW, messageUtil.getMessage("cannotHandoverNotNewEquipment"));
        HandoverTicket handoverTicket = handoverTicketMapper.createTicket(createHandoverTicketForm, equipment);
        equipmentMapper.createHandoverTicket(equipment, createHandoverTicketForm, handoverTicket);
        HandoverTicket savedHandoverTicket = handoverTicketRepository.save(handoverTicket);
        equipmentRepository.save(equipment);
        fileStorageService.uploadMultipleFiles(
                savedHandoverTicket.getClass().getSimpleName(),
                savedHandoverTicket.getId(),
                FileDescription.DOCUMENT,
                attachments
        );
        applicationEventPublisher.publishEvent(new HandoverTicketCreatedEvent(savedHandoverTicket));
        return handoverTicketMapper.toFullInfoDto(savedHandoverTicket);
    }

    @Override
    public Page<EquipmentListHandoverDto> getAllEquipmentsForHandover(
            GetEquipmentsForHandoverQueryParam getEquipmentsForHandoverQueryParam, Pageable pageable
    ) {
        Page<Equipment> equipmentPage = equipmentRepository.findAll(
                EquipmentPredicate.getAllEquipmentsForHandoverPredicate(getEquipmentsForHandoverQueryParam),
                pageable
        );
        return equipmentPage.map(equipmentMapper::toListHandoverDto);
    }

    @Override
    public HandoverTicketFullInfoDto getHandoverDetail(Long equipmentId, Long handoverId) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        HandoverTicket handoverTicket = equipment.getHandoverTickets().stream()
                .filter(ht -> ht.getId().equals(handoverId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("handoverNotFound")));
        if (handoverTicket == null) {
            throw new ResourceNotFoundException(messageUtil.getMessage("handoverNotFound"));
        }
        return handoverTicketMapper.toFullInfoDto(handoverTicket);
    }

    @Override
    @Transactional
    public HandoverTicketFullInfoDto acceptHandoverTicket(
            Long equipmentId, Long handoverId, AcceptHandoverTicketForm acceptHandoverTicketForm
    ) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        HandoverTicket handoverTicketToBeAccepted = equipment.getHandoverTickets().stream()
                .filter(ht -> ht.getId().equals(handoverId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("handoverNotFound")));
        handoverTicketMapper.acceptTicket(acceptHandoverTicketForm, handoverTicketToBeAccepted);
        validationUtil.validateEquipmentStatus(
                equipment,
                EquipmentStatus.PENDING_HANDOVER,
                "cannotAcceptHandoverTicketOnEquipmentNotPendingHandover"
        );
        equipment = equipmentMapper.acceptHandoverTicket(equipment, acceptHandoverTicketForm);
        HandoverTicket savedHandoverTicket = handoverTicketRepository.save(handoverTicketToBeAccepted);
        equipmentRepository.save(equipment);
        applicationEventPublisher.publishEvent(new HandoverTicketAcceptedEvent(savedHandoverTicket));
        return handoverTicketMapper.toFullInfoDto(savedHandoverTicket);
    }
}