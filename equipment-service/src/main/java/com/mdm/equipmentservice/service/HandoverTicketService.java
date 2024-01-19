package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.form.AcceptHandoverTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateHandoverTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.HandoverTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListHandoverDto;
import com.mdm.equipmentservice.query.param.GetEquipmentsForHandoverQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface HandoverTicketService {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"HANDOVER.READ\")")
    Page<EquipmentListHandoverDto> getAllEquipmentsForHandover(GetEquipmentsForHandoverQueryParam getEquipmentsForHandoverQueryParam, Pageable pageable);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"HANDOVER.CREATE\")")
    @Transactional
    HandoverTicketFullInfoDto createHandoverTicket(CreateHandoverTicketForm createHandoverTicketForm, Long equipmentId, MultipartFile[] attachments);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"HANDOVER.READ\")")
    HandoverTicketFullInfoDto getHandoverDetail(Long equipmentId, Long handoverId);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"HANDOVER.ACCEPT\")")
    @Transactional
    HandoverTicketFullInfoDto acceptHandoverTicket(Long equipmentId, Long handoverId, AcceptHandoverTicketForm acceptHandoverTicketForm);
}
