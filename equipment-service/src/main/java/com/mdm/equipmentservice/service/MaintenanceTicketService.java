package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.form.AcceptMaintenanceTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateMaintenanceTicketForm;
import com.mdm.equipmentservice.model.dto.form.UpdateMaintenanceTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.MaintenanceTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListMaintenanceDto;
import com.mdm.equipmentservice.model.dto.list.MaintenanceTicketListDto;
import com.mdm.equipmentservice.query.param.GetEquipmentsForMaintenanceQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface MaintenanceTicketService {


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"MAINTENANCE.READ\")")
    Page<EquipmentListMaintenanceDto> getAllEquipmentsForMaintenance(GetEquipmentsForMaintenanceQueryParam getEquipmentsForMaintenanceQueryParam, Pageable pageable);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"MAINTENANCE.CREATE\")")
    @Transactional
    MaintenanceTicketFullInfoDto createMaintenanceTicket(CreateMaintenanceTicketForm createMaintenanceTicketForm, Long equipmentId, MultipartFile[] attachments);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"MAINTENANCE.READ\")")
    MaintenanceTicketFullInfoDto getMaintenanceDetail(Long equipmentId, Long maintenanceId);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"MAINTENANCE.ACCEPT\")")
    @Transactional
    MaintenanceTicketFullInfoDto acceptMaintenanceTicket(Long equipmentId, Long maintenanceId, AcceptMaintenanceTicketForm acceptMaintenanceTicketForm);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"MAINTENANCE.READ\")")
    Page<MaintenanceTicketListDto> getAllMaintenanceTicketsOfAnEquipment(Long equipmentId, Pageable pageable);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"MAINTENANCE.UPDATE\")")
    @Transactional
    MaintenanceTicketFullInfoDto updateMaintenanceTicket(Long equipmentId, Long maintenanceId, UpdateMaintenanceTicketForm acceptMaintenanceTicketForm, MultipartFile[] attachments);

}
