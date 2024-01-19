package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.model.dto.form.AcceptHandoverTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateHandoverTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.HandoverTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListHandoverDto;
import com.mdm.equipmentservice.query.param.GetEquipmentsForHandoverQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.HandoverTicketService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping("/api/v1/equipments")
public class HandoverController {

    private final HandoverTicketService handoverTicketService;

    private final PagedResourcesAssembler<EquipmentListHandoverDto> pagedResourcesAssembler;

    @Autowired
    public HandoverController(
            HandoverTicketService handoverTicketService,
            PagedResourcesAssembler<EquipmentListHandoverDto> pagedResourcesAssembler) {
        this.handoverTicketService = handoverTicketService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping(value = {"/{equipmentId}/handovers"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse<HandoverTicketFullInfoDto>> createHandoverTicket(
            @RequestPart @Valid CreateHandoverTicketForm createHandoverTicketForm, @PathVariable Long equipmentId,
            @RequestPart(required = false) MultipartFile... attachments
    ) {
        HandoverTicketFullInfoDto handoverTicketFullInfoDto = handoverTicketService.createHandoverTicket(
                createHandoverTicketForm,
                equipmentId,
                attachments
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>(handoverTicketFullInfoDto, 201));
    }

    @GetMapping("/{equipmentId}/handovers/{handoverId}")
    public ResponseEntity<GenericResponse<HandoverTicketFullInfoDto>> getHandoverDetail(
            @PathVariable Long equipmentId, @PathVariable Long handoverId
    ) {
        HandoverTicketFullInfoDto handoverDetail = handoverTicketService.getHandoverDetail(equipmentId, handoverId);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(handoverDetail, 200));
    }

    @PutMapping(value = "/{equipmentId}/handovers/{handoverId}/accept")
    public ResponseEntity<GenericResponse<HandoverTicketFullInfoDto>> acceptHandoverTicket(
            @PathVariable Long equipmentId, @PathVariable Long handoverId, @Valid @RequestBody AcceptHandoverTicketForm acceptHandoverTicketForm
    ) {
        HandoverTicketFullInfoDto handoverTicketFullInfoDto = handoverTicketService.acceptHandoverTicket(
                equipmentId,
                handoverId,
                acceptHandoverTicketForm
        );
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(handoverTicketFullInfoDto, 200));
    }


    @GetMapping(value = "/handovers")
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<EquipmentListHandoverDto>>>> getAllEquipmentsForHandover(
            GetEquipmentsForHandoverQueryParam getEquipmentsForHandoverQueryParam, Pageable pageable
    ) {
        Page<EquipmentListHandoverDto>
                allEquipmentsForHandover = handoverTicketService.getAllEquipmentsForHandover(getEquipmentsForHandoverQueryParam, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(pagedResourcesAssembler.toModel(allEquipmentsForHandover), 200));
    }

}
