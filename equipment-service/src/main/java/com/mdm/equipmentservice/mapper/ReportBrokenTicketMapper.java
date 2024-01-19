package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.model.dto.form.AcceptReportBrokenTicketForm;
import com.mdm.equipmentservice.event.ReportBrokenTicketAcceptedEvent;
import com.mdm.equipmentservice.event.ReportBrokenTicketCreatedEvent;
import com.mdm.equipmentservice.model.dto.base.ReportBrokenTicketDto;
import com.mdm.equipmentservice.model.dto.event.ReportBrokenTicketAcceptedEventDto;
import com.mdm.equipmentservice.model.dto.event.ReportBrokenTicketCreatedEventDto;
import com.mdm.equipmentservice.model.dto.form.CreateReportBrokenTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.ReportBrokenTicketFullInfoDto;
import com.mdm.equipmentservice.model.entity.FileDescription;
import com.mdm.equipmentservice.model.entity.ReportBrokenTicket;
import com.mdm.equipmentservice.model.entity.TicketStatus;
import com.mdm.equipmentservice.model.repository.UserRepository;
import com.mdm.equipmentservice.service.FileStorageService;
import com.mdm.equipmentservice.util.CommonUtil;
import com.mdm.equipmentservice.util.SecurityUtil;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {IdToEntityMapper.class},
        imports = {SecurityUtil.class, TicketStatus.class, CommonUtil.class, FileDescription.class}
)
public abstract class ReportBrokenTicketMapper {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected FileStorageService fileStorageService;

    public abstract ReportBrokenTicketDto toDto(ReportBrokenTicket reportBrokenTicket);


    @Mapping(target = "code", expression = "java(CommonUtil.generateTicketCode())")
    @Mapping(target = "creator", expression = "java(SecurityUtil.getCurrentLoggedInUser(userRepository))")
    @Mapping(target = "status", expression = "java(TicketStatus.PENDING)")
    @Mapping(target = "equipment", source = "equipmentId")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract ReportBrokenTicket createTicket(CreateReportBrokenTicketForm createReportBrokenTicketForm, Long equipmentId);

    @Mapping(
            target = "attachments",
            expression = "java(fileStorageService.getAllFilesOfAnEntityWithoutData(reportBrokenTicket.getClass().getSimpleName(),reportBrokenTicket.getId(),FileDescription.DOCUMENT))"
    )
    public abstract ReportBrokenTicketFullInfoDto toFullInfoDto(ReportBrokenTicket reportBrokenTicket);

    @Mapping(target = "status", expression = "java(acceptReportBrokenTicketForm.getIsApproved() ? TicketStatus.ACCEPTED : TicketStatus.REJECTED)")
    @Mapping(target = "approver", expression = "java(SecurityUtil.getCurrentLoggedInUser(userRepository))")
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract ReportBrokenTicket acceptTicket(AcceptReportBrokenTicketForm acceptReportBrokenTicketForm,
                                                    @MappingTarget ReportBrokenTicket reportBrokenTicket);


    public abstract ReportBrokenTicketCreatedEventDto toEventDto(ReportBrokenTicketCreatedEvent reportBrokenTicketCreatedEvent);

    public abstract ReportBrokenTicketAcceptedEventDto toEventDto(ReportBrokenTicketAcceptedEvent reportBrokenTicketAcceptedEvent);

}