package com.mdm.equipmentservice.mapper;


import com.mdm.equipmentservice.event.InspectionTicketAcceptedEvent;
import com.mdm.equipmentservice.event.InspectionTicketCreatedEvent;
import com.mdm.equipmentservice.event.InspectionTicketUpdatedEvent;
import com.mdm.equipmentservice.model.dto.InspectionTicketDto;
import com.mdm.equipmentservice.model.dto.event.InspectionTicketCreatedEventDto;
import com.mdm.equipmentservice.model.dto.event.InspectionTicketUpdatedEventDto;
import com.mdm.equipmentservice.model.dto.form.AcceptInspectionTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateInspectionTicketForm;
import com.mdm.equipmentservice.model.dto.form.UpdateInspectionTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.InspectionTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.InspectionTicketListDto;
import com.mdm.equipmentservice.model.entity.FileDescription;
import com.mdm.equipmentservice.model.entity.InspectionTicket;
import com.mdm.equipmentservice.model.dto.event.InspectionTicketAcceptedEventDto;
import com.mdm.equipmentservice.model.entity.TicketStatus;
import com.mdm.equipmentservice.model.repository.UserRepository;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.util.CommonUtil;
import com.mdm.equipmentservice.util.SecurityUtil;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {IdToEntityMapper.class}, imports = {
        TicketStatus.class, CommonUtil.class, SecurityUtil.class, FileDescription.class
})

public abstract class InspectionTicketMapper {
    @Autowired
    protected FileStorageService fileStorageService;

    @Autowired
    protected UserRepository userRepository;

    public abstract InspectionTicketDto toDto(InspectionTicket inspectionTicket);

    @Mapping(target = "attachments", expression = "java(fileStorageService.getAllFilesOfAnEntityWithoutData(inspectionTicket.getClass().getSimpleName(), inspectionTicket.getId(), FileDescription.DOCUMENT))")
    public abstract InspectionTicketFullInfoDto toFullInfoDto(InspectionTicket inspectionTicket);


    @Mapping(target = "code", expression = "java(CommonUtil.generateTicketCode())")
    @Mapping(target = "creator", expression = "java(SecurityUtil.getCurrentLoggedInUser(userRepository))")
    @Mapping(target = "status", expression = "java(TicketStatus.PENDING)")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract InspectionTicket createTicket(CreateInspectionTicketForm createInspectionTicketForm);


    @Mapping(target = "status", expression = "java(acceptInspectionTicketForm.getIsApproved() ? TicketStatus.ACCEPTED : TicketStatus.REJECTED)")
    @Mapping(target = "approver", expression = "java(SecurityUtil.getCurrentLoggedInUser(userRepository))")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract InspectionTicket acceptTicket(
            AcceptInspectionTicketForm acceptInspectionTicketForm, @MappingTarget InspectionTicket inspectionTicket
    );

    @Mapping(target = "inspectionCompany", source = "inspectionCompanyId")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract InspectionTicket updateTicket(UpdateInspectionTicketForm updateInspectionTicketForm, @MappingTarget InspectionTicket inspectionTicket);


    @Mapping(target = "attachments", expression = "java(fileStorageService.getAllFilesOfAnEntityWithoutData(inspectionTicket.getClass().getSimpleName(), inspectionTicket.getId(), FileDescription.DOCUMENT))")
    public abstract InspectionTicketListDto toListDto(InspectionTicket inspectionTicket);


    public abstract InspectionTicketAcceptedEventDto toEventDto(InspectionTicketAcceptedEvent inspectionTicketAcceptedEvent);
    public abstract InspectionTicketCreatedEventDto toEventDto(InspectionTicketCreatedEvent inspectionTicketCreatedEvent);

    public abstract InspectionTicketUpdatedEventDto toEventDto(InspectionTicketUpdatedEvent inspectionTicketUpdatedEvent);

}