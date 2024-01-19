package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.form.AcceptRepairTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateRepairTicketForm;
import com.mdm.equipmentservice.model.dto.form.UpdateRepairTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.RepairTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListRepairDto;
import com.mdm.equipmentservice.query.param.GetEquipmentsForRepairQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface RepairTicketService {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"REPAIR.READ\")")
    Page<EquipmentListRepairDto> getAllEquipmentsForRepair(GetEquipmentsForRepairQueryParam getEquipmentsForRepairQueryParam, Pageable pageable);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"REPAIR.CREATE\")")
    @Transactional
    RepairTicketFullInfoDto createRepairTicket(CreateRepairTicketForm createRepairTicketForm, Long equipmentId, MultipartFile[] attachments);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"REPAIR.READ\")")
    RepairTicketFullInfoDto getRepairDetail(Long equipmentId, Long repairId);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"REPAIR.ACCEPT\")")
    @Transactional
    RepairTicketFullInfoDto acceptRepairTicket(Long equipmentId, Long repairId, AcceptRepairTicketForm acceptRepairTicketForm);
    @PreAuthorize("hasAuthority(\"REPAIR.UPDATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    RepairTicketFullInfoDto updateRepairTicket(Long repairTicketId, Long equipmentId, UpdateRepairTicketForm updateRepairTicketForm, MultipartFile[] attachments);

    @PreAuthorize("hasAuthority(\"REPAIR.ACCEPTANCE_TESTING\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void acceptanceTesting(Long repairTicketId, Long equipmentId, MultipartFile[] attachments);
}
