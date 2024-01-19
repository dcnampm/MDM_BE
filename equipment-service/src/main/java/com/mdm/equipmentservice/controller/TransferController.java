package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.model.dto.form.AcceptTransferTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateTransferTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.TransferTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListTransferDto;
import com.mdm.equipmentservice.query.param.GetEquipmentsForTransferQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.TransferTicketService;
import com.mdm.equipmentservice.util.MessageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/equipments")
public class TransferController {
    private final MessageUtil messageUtil;

    private final TransferTicketService transferTicketService;

    private final PagedResourcesAssembler<EquipmentListTransferDto> pagedResourcesAssembler;

    @Autowired
    public TransferController(MessageUtil messageUtil, TransferTicketService transferTicketService, PagedResourcesAssembler<EquipmentListTransferDto> pagedResourcesAssembler) {
        this.messageUtil = messageUtil;
        this.transferTicketService = transferTicketService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping(value = "{equipmentId}/transfers", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse<TransferTicketFullInfoDto>> createTransferTicket(@PathVariable Long equipmentId, @RequestPart
    @Valid CreateTransferTicketForm createTransferTicketForm, @RequestPart(required = false) MultipartFile[] attachments) {
        TransferTicketFullInfoDto transferTicket =
                transferTicketService.createTransferTicket(createTransferTicketForm, equipmentId, attachments);
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>(transferTicket, 201));
    }


    @GetMapping("/transfers")
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<EquipmentListTransferDto>>>> getAllEquipmentForTransfer(
            GetEquipmentsForTransferQueryParam getEquipmentsForTransferQueryParam, Pageable pageable
    ) {
        Page<EquipmentListTransferDto> allEquipmentsForTransfer = transferTicketService.getAllEquipmentsForTransfer(
                getEquipmentsForTransferQueryParam,
                pageable
        );
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(pagedResourcesAssembler.toModel(allEquipmentsForTransfer), 200));

    }


    @GetMapping("/{equipmentId}/transfers/{transferId}")
    public ResponseEntity<GenericResponse<TransferTicketFullInfoDto>> getTransferDetail(
            @PathVariable Long equipmentId, @PathVariable Long transferId
    ) {
        TransferTicketFullInfoDto transferDetail = transferTicketService.getTransferDetail(equipmentId, transferId);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(transferDetail, 200));
    }

    @PutMapping(value = "/{equipmentId}/transfers/{transferId}/accept")
    public ResponseEntity<GenericResponse<TransferTicketFullInfoDto>> acceptTransferTicket(
            @PathVariable Long equipmentId, @PathVariable Long transferId, @Valid @RequestBody AcceptTransferTicketForm acceptTransferTicketForm
    ) {
        TransferTicketFullInfoDto transferTicketFullInfoDto = transferTicketService.acceptTransferTicket(
                equipmentId,
                transferId,
                acceptTransferTicketForm
        );
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(transferTicketFullInfoDto, 200));
    }
}
