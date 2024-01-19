package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.form.AcceptLiquidationTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateLiquidationTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.LiquidationTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListLiquidationDto;
import com.mdm.equipmentservice.query.param.GetEquipmentsForLiquidationQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface LiquidationTicketService {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"LIQUIDATION.READ\")")
    Page<EquipmentListLiquidationDto> getAllEquipmentsForLiquidation(GetEquipmentsForLiquidationQueryParam getEquipmentsForLiquidationQueryParam, Pageable pageable);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"LIQUIDATION.CREATE\")")
    @Transactional
    LiquidationTicketFullInfoDto createLiquidationTicket(CreateLiquidationTicketForm createLiquidationTicketForm, Long equipmentId, MultipartFile[] attachments);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"LIQUIDATION.READ\")")
    LiquidationTicketFullInfoDto getLiquidationDetail(Long equipmentId, Long liquidationId);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"LIQUIDATION.ACCEPT\")")
    @Transactional
    LiquidationTicketFullInfoDto acceptLiquidationTicket(Long equipmentId, Long liquidationId, AcceptLiquidationTicketForm acceptLiquidationTicketForm);
}
