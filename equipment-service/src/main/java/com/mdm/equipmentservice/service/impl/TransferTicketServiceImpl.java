package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.event.TransferTicketAcceptedEvent;
import com.mdm.equipmentservice.event.TransferTicketCreatedEvent;
import com.mdm.equipmentservice.exception.ResourceNotFoundException;
import com.mdm.equipmentservice.mapper.EquipmentMapper;
import com.mdm.equipmentservice.mapper.TransferTicketMapper;
import com.mdm.equipmentservice.model.dto.form.AcceptTransferTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateTransferTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.TransferTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListTransferDto;
import com.mdm.equipmentservice.model.entity.*;
import com.mdm.equipmentservice.model.repository.EquipmentRepository;
import com.mdm.equipmentservice.model.repository.TransferRepository;
import com.mdm.equipmentservice.model.repository.UserRepository;
import com.mdm.equipmentservice.query.param.GetEquipmentsForTransferQueryParam;
import com.mdm.equipmentservice.query.predicate.EquipmentPredicate;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.service.TransferTicketService;
import com.mdm.equipmentservice.util.MessageUtil;
import com.mdm.equipmentservice.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class TransferTicketServiceImpl implements TransferTicketService {

    private final UserRepository userRepository;

    private final EquipmentRepository equipmentRepository;

    private final TransferRepository transferRepository;

    private final MessageUtil messageUtil;

    private final TransferTicketMapper transferTicketMapper;

    private final FileStorageService fileStorageService;

    private final EquipmentMapper equipmentMapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public TransferTicketServiceImpl(TransferRepository transferRepository, MessageUtil messageUtil, TransferTicketMapper transferTicketMapper,
                                     EquipmentRepository equipmentRepository, FileStorageService fileStorageService,
                                     ApplicationEventPublisher applicationEventPublisher, UserRepository userRepository, EquipmentMapper equipmentMapper) {
        this.transferRepository = transferRepository;
        this.messageUtil = messageUtil;
        this.transferTicketMapper = transferTicketMapper;
        this.equipmentRepository = equipmentRepository;
        this.fileStorageService = fileStorageService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.userRepository = userRepository;
        this.equipmentMapper = equipmentMapper;
    }

    @Override
    public Page<EquipmentListTransferDto> getAllEquipmentsForTransfer(GetEquipmentsForTransferQueryParam getEquipmentsForTransferQueryParam,
                                                                      Pageable pageable) {
        Page<Equipment> equipmentPage =
                equipmentRepository.findAll(EquipmentPredicate.getAllEquipmentsForTransferPredicate(getEquipmentsForTransferQueryParam), pageable);
        return equipmentPage.map(equipmentMapper::toListTransferDto);
    }
    @Override
    @Transactional
    public TransferTicketFullInfoDto createTransferTicket(CreateTransferTicketForm createTransferTicketForm, Long equipmentId, MultipartFile[] attachments) {
        Equipment equipment =
                equipmentRepository.findById(equipmentId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        equipment.setStatus(EquipmentStatus.PENDING_TRANSFER);
        TransferTicket transferTicket = transferTicketMapper.createTicket(createTransferTicketForm, equipment.getDepartment(), equipment);
        TransferTicket savedTransferTicket = transferRepository.save(transferTicket);
        fileStorageService.uploadMultipleFiles(savedTransferTicket.getClass().getSimpleName(),
                savedTransferTicket.getId(),
                FileDescription.DOCUMENT,
                attachments
        );
        applicationEventPublisher.publishEvent(new TransferTicketCreatedEvent(savedTransferTicket));
        return transferTicketMapper.toFullInfoDto(savedTransferTicket);
    }

    @Override
    public TransferTicketFullInfoDto getTransferDetail(Long equipmentId, Long transferId) {
        Equipment equipment =
                equipmentRepository.findById(equipmentId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        TransferTicket transferTicket = equipment.getTransferTickets()
                .stream()
                .filter(transferTicket1 -> transferTicket1.getId().equals(transferId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("transferNotFound")));
        if (transferTicket == null) {
            throw new ResourceNotFoundException(messageUtil.getMessage("transferNotFound"));
        }
        return transferTicketMapper.toFullInfoDto(transferTicket);
    }

    @Override
    @Transactional
    public TransferTicketFullInfoDto acceptTransferTicket(Long equipmentId, Long transferId, AcceptTransferTicketForm acceptTransferTicketForm) {
        Equipment equipment =
                equipmentRepository.findById(equipmentId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("equipmentNotFound")));
        TransferTicket transferTicket = equipment.getTransferTickets()
                .stream()
                .filter(t -> t.getId().equals(transferId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("transferNotFound")));
        validateIfCurrentLoggedInUserCanAcceptTheTransferTicket(transferTicket);
        transferTicket = transferTicketMapper.acceptTransfer(acceptTransferTicketForm, transferTicket);
        equipment.setStatus(EquipmentStatus.IN_USE);
        if (acceptTransferTicketForm.getIsApproved()) {
            equipment.setDepartment(transferTicket.getToDepartment());
        }
        equipmentRepository.save(equipment);
        applicationEventPublisher.publishEvent(new TransferTicketAcceptedEvent(transferTicket));
        return transferTicketMapper.toFullInfoDto(transferTicket);
    }

    /**
     * The current logged-in user can only accept the transferTicket ticket if the user is in the department that the equipment is being transferred to.
     */
    private void validateIfCurrentLoggedInUserCanAcceptTheTransferTicket(TransferTicket transferTicket) {
        User currentLoggedInUser = SecurityUtil.getCurrentLoggedInUser(userRepository);
        if (SecurityUtil.currentUserHasAnyAuthorities("ROLE_ADMIN", "ROLE_TPVT")) {
            return;
        }
        if (!currentLoggedInUser.getDepartment().getId().equals(transferTicket.getToDepartment().getId())) {
            throw new org.springframework.security.access.AccessDeniedException(messageUtil.getMessage("youDoNotHavePermissionToAcceptThisTransferTicket"));
        }
    }
/*
    @Override
    public Page<TransferTicketListDto> getTransferTicketList(GetTransfersQueryParam getTransfersQueryParam, Pageable pageable) {
        Page<TransferTicket> transferPage = transferRepository.findAll(TransferPredicate.getTransferPredicate(getTransfersQueryParam), pageable);
        return transferPage.map(transferTicketMapper::toListDto);
    }

    @Override
    public Page<TransferTicketListDto> getAllTransferTicketsOfAnEquipment(Long equipmentId, Pageable pageable) {
        Page<TransferTicket> transfers = transferRepository.findByEquipment_Id(equipmentId, pageable);
        return transfers.map(transferTicketMapper::toListDto);
    }*/
}
