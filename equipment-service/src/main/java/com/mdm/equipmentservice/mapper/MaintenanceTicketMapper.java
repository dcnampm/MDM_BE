package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.event.MaintenanceTicketAcceptedEvent;
import com.mdm.equipmentservice.event.MaintenanceTicketCreatedEvent;
import com.mdm.equipmentservice.event.MaintenanceTicketUpdatedEvent;
import com.mdm.equipmentservice.model.dto.base.MaintenanceTicketDto;
import com.mdm.equipmentservice.model.dto.event.MaintenanceTicketAcceptedEventDto;
import com.mdm.equipmentservice.model.dto.event.MaintenanceTicketCreatedEventDto;
import com.mdm.equipmentservice.model.dto.event.MaintenanceTicketUpdatedEventDto;
import com.mdm.equipmentservice.model.dto.form.AcceptMaintenanceTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateMaintenanceTicketForm;
import com.mdm.equipmentservice.model.dto.form.UpdateMaintenanceTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.MaintenanceTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.MaintenanceTicketListDto;
import com.mdm.equipmentservice.model.entity.FileDescription;
import com.mdm.equipmentservice.model.entity.MaintenanceTicket;
import com.mdm.equipmentservice.model.entity.TicketStatus;
import com.mdm.equipmentservice.model.repository.UserRepository;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.util.CommonUtil;
import com.mdm.equipmentservice.util.SecurityUtil;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {IdToEntityMapper.class}, imports = {
        TicketStatus.class, CommonUtil.class, SecurityUtil.class, FileDescription.class
})

public abstract class MaintenanceTicketMapper {
    @Autowired
    protected FileStorageService fileStorageService;

    @Autowired
    protected UserRepository userRepository;

    public abstract MaintenanceTicketDto toDto(MaintenanceTicket maintenanceTicket);

    @Mapping(target = "attachments", expression = "java(fileStorageService.getAllFilesOfAnEntityWithoutData(maintenanceTicket.getClass().getSimpleName(), maintenanceTicket.getId(), FileDescription.DOCUMENT))")
    public abstract MaintenanceTicketFullInfoDto toFullInfoDto(MaintenanceTicket maintenanceTicket);

    @Mapping(target = "attachments", expression = "java(fileStorageService.getAllFilesOfAnEntityWithoutData(maintenanceTicket.getClass().getSimpleName(), maintenanceTicket.getId(), FileDescription.DOCUMENT))")
    public abstract MaintenanceTicketListDto toListDto(MaintenanceTicket maintenanceTicket);

    @Mapping(target = "code", expression = "java(CommonUtil.generateTicketCode())")
    @Mapping(target = "creator", expression = "java(SecurityUtil.getCurrentLoggedInUser(userRepository))")
    @Mapping(target = "status", expression = "java(TicketStatus.PENDING)")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract MaintenanceTicket createTicket(CreateMaintenanceTicketForm createMaintenanceTicketForm);


    @Mapping(target = "status", expression = "java(acceptMaintenanceTicketForm.getIsApproved() ? TicketStatus.ACCEPTED : TicketStatus.REJECTED)")
    @Mapping(target = "approver", expression = "java(SecurityUtil.getCurrentLoggedInUser(userRepository))")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract MaintenanceTicket acceptTicket(
            AcceptMaintenanceTicketForm acceptMaintenanceTicketForm, @MappingTarget MaintenanceTicket maintenanceTicket
    );

    @Mapping(target = "maintenanceCompany", source = "maintenanceCompanyId")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract MaintenanceTicket updateTicket(UpdateMaintenanceTicketForm updateMaintenanceTicketForm, @MappingTarget MaintenanceTicket maintenanceTicket);

    public abstract MaintenanceTicketAcceptedEventDto toEventDto(MaintenanceTicketAcceptedEvent maintenanceTicketAcceptedEvent);

    public abstract MaintenanceTicketCreatedEventDto toEventDto(MaintenanceTicketCreatedEvent maintenanceTicketCreatedEvent);

    public abstract MaintenanceTicketUpdatedEventDto toEventDto(MaintenanceTicketUpdatedEvent maintenanceTicketUpdatedEvent);

}