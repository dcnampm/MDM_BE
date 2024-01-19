package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.event.RepairTicketAcceptanceTestingEvent;
import com.mdm.equipmentservice.event.RepairTicketAcceptedEvent;
import com.mdm.equipmentservice.event.RepairTicketCreatedEvent;
import com.mdm.equipmentservice.event.RepairTicketUpdatedEvent;
import com.mdm.equipmentservice.model.dto.base.RepairTicketDto;
import com.mdm.equipmentservice.model.dto.event.RepairTicketAcceptedEventDto;
import com.mdm.equipmentservice.model.dto.form.AcceptRepairTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateRepairTicketForm;
import com.mdm.equipmentservice.model.dto.form.UpdateRepairTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.RepairTicketFullInfoDto;
import com.mdm.equipmentservice.model.entity.FileDescription;
import com.mdm.equipmentservice.model.entity.RepairStatus;
import com.mdm.equipmentservice.model.entity.RepairTicket;
import com.mdm.equipmentservice.model.entity.TicketStatus;
import com.mdm.equipmentservice.model.repository.UserRepository;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.util.CommonUtil;
import com.mdm.equipmentservice.util.SecurityUtil;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {
        FileDescription.class,
        CommonUtil.class,
        SecurityUtil.class,
        TicketStatus.class, RepairStatus.class
}, uses = {IdToEntityMapper.class})
public abstract class RepairTicketMapper {
    @Autowired
    protected FileStorageService fileStorageService;

    @Autowired
    protected UserRepository userRepository;

    @Mapping(target = "attachmentIds", expression = "java(fileStorageService.getAllFileIdsOfAnEntity(repairTicket.getClass().getSimpleName(),repairTicket.getId(),FileDescription.DOCUMENT))")
    public abstract RepairTicketDto toDto(RepairTicket repairTicket);

    @Mapping(target = "status", expression = "java(TicketStatus.PENDING)")
    @Mapping(target = "equipment", source = "equipmentId")
    @Mapping(target = "creator", expression = "java(SecurityUtil.getCurrentLoggedInUser(userRepository))")
    @Mapping(target = "code", expression = "java(CommonUtil.generateTicketCode())")
    @Mapping(target = "repairCompany", source = "createRepairTicketForm.repairCompanyId")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract RepairTicket createTicket(CreateRepairTicketForm createRepairTicketForm, Long equipmentId);

    @Mapping(target = "attachments", expression = "java(fileStorageService.getAllFilesOfAnEntityWithoutData(repairTicket.getClass().getSimpleName(),repairTicket.getId(),FileDescription.DOCUMENT))")
    public abstract RepairTicketFullInfoDto toFullInfoDto(RepairTicket repairTicket);

    @Mapping(target = "status", expression = "java(acceptRepairTicketForm.getIsAccepted() ? TicketStatus.ACCEPTED : TicketStatus.REJECTED)")
    @Mapping(target = "repairStatus", expression = "java(acceptRepairTicketForm.getIsAccepted() ? RepairStatus.IN_PROGRESS : null)")
    @Mapping(target = "approver", expression = "java(SecurityUtil.getCurrentLoggedInUser(userRepository))")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract RepairTicket acceptTicket(AcceptRepairTicketForm acceptRepairTicketForm, @MappingTarget RepairTicket repairTicket);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract RepairTicket updateTicket(
            UpdateRepairTicketForm updateRepairTicketForm, @MappingTarget RepairTicket repairTicket
    );

    public abstract RepairTicketAcceptanceTestingEvent toEventDto(RepairTicketAcceptanceTestingEvent repairTicketAcceptanceTestingEvent);

    public abstract RepairTicketAcceptedEventDto toEventDto(RepairTicketAcceptedEvent repairTicketAcceptedEvent);

    public abstract RepairTicketCreatedEvent toEventDto(RepairTicketCreatedEvent repairTicketCreatedEvent);

    public abstract RepairTicketUpdatedEvent toEventDto(RepairTicketUpdatedEvent repairTicketUpdatedEvent);

}