package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.model.dto.form.AcceptLiquidationTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateLiquidationTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.LiquidationTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListLiquidationDto;
import com.mdm.equipmentservice.query.param.GetEquipmentsForLiquidationQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.LiquidationTicketService;
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
public class LiquidationController {
    private final MessageUtil messageUtil;

    private final LiquidationTicketService liquidationTicketService;
    private final PagedResourcesAssembler<EquipmentListLiquidationDto> pagedResourcesAssembler;

    @Autowired
    public LiquidationController(MessageUtil messageUtil, LiquidationTicketService liquidationTicketService,
                                 PagedResourcesAssembler<EquipmentListLiquidationDto> pagedResourcesAssembler) {
        this.messageUtil = messageUtil;
        this.liquidationTicketService = liquidationTicketService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping(value = "{equipmentId}/liquidations", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse<LiquidationTicketFullInfoDto>> createLiquidationTicket(@PathVariable Long equipmentId, @RequestPart
    @Valid CreateLiquidationTicketForm createLiquidationTicketForm, @RequestPart(required = false) MultipartFile[] attachments) {
        LiquidationTicketFullInfoDto liquidationTicket =
                liquidationTicketService.createLiquidationTicket(createLiquidationTicketForm, equipmentId, attachments);
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>(liquidationTicket, 201));
    }


    @GetMapping("/liquidations")
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<EquipmentListLiquidationDto>>>> getAllEquipmentForLiquidation(
            GetEquipmentsForLiquidationQueryParam getEquipmentsForLiquidationQueryParam, Pageable pageable
    ) {
        Page<EquipmentListLiquidationDto> allEquipmentsForLiquidation = liquidationTicketService.getAllEquipmentsForLiquidation(
                getEquipmentsForLiquidationQueryParam,
                pageable
        );
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(pagedResourcesAssembler.toModel(allEquipmentsForLiquidation), 200));

    }


    @GetMapping("/{equipmentId}/liquidations/{liquidationId}")
    public ResponseEntity<GenericResponse<LiquidationTicketFullInfoDto>> getLiquidationDetail(
            @PathVariable Long equipmentId, @PathVariable Long liquidationId
    ) {
        LiquidationTicketFullInfoDto liquidationDetail = liquidationTicketService.getLiquidationDetail(equipmentId, liquidationId);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(liquidationDetail, 200));
    }

    @PutMapping(value = "/{equipmentId}/liquidations/{liquidationId}/accept")
    public ResponseEntity<GenericResponse<LiquidationTicketFullInfoDto>> acceptLiquidationTicket(
            @PathVariable Long equipmentId, @PathVariable Long liquidationId, @Valid @RequestBody AcceptLiquidationTicketForm acceptLiquidationTicketForm
    ) {
        LiquidationTicketFullInfoDto liquidationTicketFullInfoDto = liquidationTicketService.acceptLiquidationTicket(
                equipmentId,
                liquidationId,
                acceptLiquidationTicketForm
        );
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(liquidationTicketFullInfoDto, 200));
    }
}
