package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.form.AcceptInspectionTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateInspectionTicketForm;
import com.mdm.equipmentservice.model.dto.form.UpdateInspectionTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.InspectionTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListInspectionDto;
import com.mdm.equipmentservice.model.dto.list.InspectionTicketListDto;
import com.mdm.equipmentservice.query.param.GetEquipmentsForInspectionQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface InspectionTicketService {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"INSPECTION.READ\")")
    Page<EquipmentListInspectionDto> getAllEquipmentsForInspection(
            GetEquipmentsForInspectionQueryParam getEquipmentsForInspectionQueryParam, Pageable pageable
    );

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"INSPECTION.CREATE\")")
    @Transactional
    InspectionTicketFullInfoDto createInspectionTicket(CreateInspectionTicketForm createInspectionTicketForm, Long equipmentId, MultipartFile[] attachments);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"INSPECTION.READ\")")
    InspectionTicketFullInfoDto getInspectionDetail(Long equipmentId, Long inspectionId);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"INSPECTION.ACCEPT\")")
    @Transactional
    InspectionTicketFullInfoDto acceptInspectionTicket(Long equipmentId, Long inspectionId, AcceptInspectionTicketForm acceptInspectionTicketForm);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"INSPECTION.READ\")")
    Page<InspectionTicketListDto> getAllInspectionTicketsOfAnEquipment(Long equipmentId, Pageable pageable);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"INSPECTION.UPDATE\")")
    @Transactional
    InspectionTicketFullInfoDto updateInspectionTicket(Long equipmentId, Long inspectionId, UpdateInspectionTicketForm acceptInspectionTicketForm, MultipartFile[] attachments);
}
