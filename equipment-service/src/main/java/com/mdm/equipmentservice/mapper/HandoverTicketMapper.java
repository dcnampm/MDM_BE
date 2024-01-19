package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.event.HandoverTicketAcceptedEvent;
import com.mdm.equipmentservice.event.HandoverTicketCreatedEvent;
import com.mdm.equipmentservice.model.dto.event.HandoverTicketAcceptedEventDto;
import com.mdm.equipmentservice.model.dto.event.HandoverTicketCreatedEventDto;
import com.mdm.equipmentservice.model.dto.form.AcceptHandoverTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateHandoverTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.HandoverTicketFullInfoDto;
import com.mdm.equipmentservice.model.entity.*;
import com.mdm.equipmentservice.model.repository.UserRepository;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.util.CommonUtil;
import com.mdm.equipmentservice.util.SecurityUtil;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING, uses = IdToEntityMapper.class, imports = {
        HandoverStatus.class,
        SecurityUtil.class,
        FileDescription.class,
        LocalDateTime.class,
        TicketStatus.class,
        CommonUtil.class
}
)
public abstract class HandoverTicketMapper {

    @Autowired
    protected FileStorageService fileStorageService;

    @Autowired
    protected UserRepository userRepository;

    @Mapping(target = "responsiblePerson", source = "createHandoverTicketForm.responsiblePersonId")
    @Mapping(target = "code", expression = "java(CommonUtil.generateTicketCode())")
    @Mapping(target = "creator", expression = "java(SecurityUtil.getCurrentLoggedInUser(userRepository))")
    @Mapping(target = "status", expression = "java(TicketStatus.PENDING)")
    @Mapping(target = "equipment", source = "equipment")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "department", source = "createHandoverTicketForm.departmentId")
    public abstract HandoverTicket createTicket(CreateHandoverTicketForm createHandoverTicketForm, Equipment equipment);

    @Mapping(
            target = "attachments",
            expression = "java(fileStorageService.getAllFilesOfAnEntityWithoutData(handoverTicket.getClass().getSimpleName(),handoverTicket.getId(),FileDescription.DOCUMENT))"
    )
    public abstract HandoverTicketFullInfoDto toFullInfoDto(HandoverTicket handoverTicket);

    @Mapping(target = "status", expression = "java(acceptHandoverTicketForm.getIsApproved() ? TicketStatus.ACCEPTED : TicketStatus.REJECTED)")
    @Mapping(target = "approver", expression = "java(SecurityUtil.getCurrentLoggedInUser(userRepository))")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract HandoverTicket acceptTicket(
            AcceptHandoverTicketForm acceptHandoverTicketForm, @MappingTarget HandoverTicket handoverTicket
    );


    public abstract HandoverTicketCreatedEventDto toEventDto(HandoverTicketCreatedEvent handoverTicketCreatedEvent);

    public abstract HandoverTicketAcceptedEventDto toEventDto(HandoverTicketAcceptedEvent handoverTicketAcceptedEvent);

}