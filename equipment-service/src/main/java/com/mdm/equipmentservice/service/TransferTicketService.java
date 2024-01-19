package com.mdm.equipmentservice.service;


import com.mdm.equipmentservice.model.dto.form.AcceptTransferTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateTransferTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.TransferTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListTransferDto;
import com.mdm.equipmentservice.query.param.GetEquipmentsForTransferQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


public interface TransferTicketService {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"TRANSFER.READ\")")
    Page<EquipmentListTransferDto> getAllEquipmentsForTransfer(GetEquipmentsForTransferQueryParam getEquipmentsForTransferQueryParam, Pageable pageable);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"TRANSFER.CREATE\")")
    @Transactional
    TransferTicketFullInfoDto createTransferTicket(CreateTransferTicketForm createTransferTicketForm, Long equipmentId, MultipartFile[] attachments);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"TRANSFER.READ\")")
    TransferTicketFullInfoDto getTransferDetail(Long equipmentId, Long transferId);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT') or hasAuthority(\"TRANSFER.ACCEPT\")")
    @Transactional
    TransferTicketFullInfoDto acceptTransferTicket(Long equipmentId, Long transferId, AcceptTransferTicketForm acceptTransferTicketForm);
}
