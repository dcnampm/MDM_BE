package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.event.LiquidationTicketAcceptedEvent;
import com.mdm.equipmentservice.event.LiquidationTicketCreatedEvent;
import com.mdm.equipmentservice.model.dto.base.LiquidationDto;
import com.mdm.equipmentservice.model.dto.event.LiquidationTicketAcceptedEventDto;
import com.mdm.equipmentservice.model.dto.event.LiquidationTicketCreatedEventDto;
import com.mdm.equipmentservice.model.dto.form.AcceptLiquidationTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateLiquidationTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.LiquidationTicketFullInfoDto;
import com.mdm.equipmentservice.model.entity.FileDescription;
import com.mdm.equipmentservice.model.entity.LiquidationTicket;
import com.mdm.equipmentservice.model.entity.TicketStatus;
import com.mdm.equipmentservice.model.repository.UserRepository;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.util.SecurityUtil;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {IdToEntityMapper.class}, imports = {
        FileDescription.class, SecurityUtil.class, TicketStatus.class
})
public abstract class LiquidationTicketMapper {

    @Autowired
    protected UserRepository userRepository;


    @Autowired
    protected FileStorageService fileStorageService;


    @Mapping(target = "status", expression = "java(TicketStatus.PENDING)")
    @Mapping(target = "creator", expression = "java(SecurityUtil.getCurrentLoggedInUser(userRepository))")
    @Mapping(target = "code", expression = "java(com.mdm.equipmentservice.util.CommonUtil.generateTicketCode())")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract LiquidationTicket createTicket(CreateLiquidationTicketForm createLiquidationTicketForm);

    public abstract LiquidationDto toDto(LiquidationTicket liquidationTicket);

    @Mapping(target = "attachments", expression = "java(fileStorageService.getAllFilesOfAnEntityWithoutData(liquidationTicket.getClass().getSimpleName(), liquidationTicket.getId(), FileDescription.DOCUMENT))")
    public abstract LiquidationTicketFullInfoDto toFullInfoDto(LiquidationTicket liquidationTicket);


    @Mapping(target = "status", expression = "java(acceptLiquidationTicketForm.getIsApproved() ? TicketStatus.ACCEPTED : TicketStatus.REJECTED)")
    @Mapping(target = "approver", expression = "java(SecurityUtil.getCurrentLoggedInUser(userRepository))")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract LiquidationTicket acceptTicket(
            AcceptLiquidationTicketForm acceptLiquidationTicketForm, @MappingTarget LiquidationTicket liquidationTicket
    );

    @Mapping(target = "status", expression = "java(acceptLiquidationTicketForm.getIsApproved() ? TicketStatus.ACCEPTED : TicketStatus.REJECTED)")
    @Mapping(target = "approver", expression = "java(SecurityUtil.getCurrentLoggedInUser(userRepository))")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract LiquidationTicket partialUpdate(
            AcceptLiquidationTicketForm acceptLiquidationTicketForm, @MappingTarget LiquidationTicket liquidationTicket
    );


    public abstract LiquidationTicketAcceptedEventDto toEventDto(LiquidationTicketAcceptedEvent liquidationTicketAcceptedEvent);

    public abstract LiquidationTicketCreatedEventDto toEventDto(LiquidationTicketCreatedEvent liquidationTicketCreatedEvent);

}