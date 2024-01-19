package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.event.TransferTicketCreatedEvent;
import com.mdm.equipmentservice.model.dto.event.TransferTicketCreatedEventDto;
import com.mdm.equipmentservice.model.dto.form.AcceptTransferTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateTransferTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.TransferTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.TransferTicketListDto;
import com.mdm.equipmentservice.model.entity.*;
import com.mdm.equipmentservice.model.repository.UserRepository;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.util.CommonUtil;
import com.mdm.equipmentservice.util.SecurityUtil;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {IdToEntityMapper.class}, imports = {
        TicketStatus.class, CommonUtil.class, SecurityUtil.class, FileDescription.class
})
public abstract class TransferTicketMapper {
    @Autowired
    protected FileStorageService fileStorageService;

    @Autowired
    protected UserRepository userRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", expression = "java(CommonUtil.generateTicketCode())")
    @Mapping(target = "status", expression = "java(TicketStatus.PENDING)")
    @Mapping(target = "fromDepartment", source = "fromDepartment")
    @Mapping(target = "toDepartment", source = "createTransferTicketForm.toDepartmentId")
    @Mapping(target = "creator", expression = "java(SecurityUtil.getCurrentLoggedInUser(userRepository))")
    @Mapping(target = "equipment", source = "equipment")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract TransferTicket createTicket(CreateTransferTicketForm createTransferTicketForm, Department fromDepartment, Equipment equipment);

    @Mapping(target = "attachments", expression = "java(fileStorageService.getAllFilesOfAnEntityWithoutData(transferTicket.getClass().getSimpleName(), transferTicket.getId(), FileDescription.DOCUMENT))")
    public abstract TransferTicketFullInfoDto toFullInfoDto(TransferTicket transferTicket);


    @Mapping(target = "approver", expression = "java(SecurityUtil.getCurrentLoggedInUser(userRepository))")
    @Mapping(target = "status", expression = "java(acceptTransferTicketForm.getIsApproved() ? TicketStatus.ACCEPTED : TicketStatus.REJECTED)")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract TransferTicket acceptTransfer(
            AcceptTransferTicketForm acceptTransferTicketForm, @MappingTarget TransferTicket transferTicket
    );

    @Mapping(target = "attachments", expression = "java(fileStorageService.getAllFilesOfAnEntityWithoutData(transferTicket.getClass().getSimpleName(), transferTicket.getId(), FileDescription.DOCUMENT))")
    public abstract TransferTicketListDto toListDto(TransferTicket transferTicket);

    public abstract TransferTicketCreatedEventDto toEventDto(TransferTicketCreatedEvent transferTicketCreatedEvent);

}