package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.form.AcceptReportBrokenTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateReportBrokenTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.ReportBrokenTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListReportBrokenDto;
import com.mdm.equipmentservice.query.param.GetEquipmentsForReportBrokenQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface ReportBrokenTicketService {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"REPORT_BROKEN.READ\")")
    Page<EquipmentListReportBrokenDto> getAllEquipmentsForReportBroken(GetEquipmentsForReportBrokenQueryParam getEquipmentsForReportBrokenQueryParam, Pageable pageable);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"REPORT_BROKEN.CREATE\")")
    @Transactional
    ReportBrokenTicketFullInfoDto createReportBrokenTicket(CreateReportBrokenTicketForm createReportBrokenTicketForm, Long equipmentId, MultipartFile[] attachments);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"REPORT_BROKEN.READ\")")
    ReportBrokenTicketFullInfoDto getReportBrokenDetail(Long equipmentId, Long reportBrokenId);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"REPORT_BROKEN.ACCEPT\")")
    @Transactional
    ReportBrokenTicketFullInfoDto acceptReportBrokenTicket(Long equipmentId, Long reportBrokenId, AcceptReportBrokenTicketForm acceptReportBrokenTicketForm);
}
