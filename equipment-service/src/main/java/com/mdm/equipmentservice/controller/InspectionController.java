package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.model.dto.form.AcceptInspectionTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateInspectionTicketForm;
import com.mdm.equipmentservice.model.dto.form.UpdateInspectionTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.InspectionTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListInspectionDto;
import com.mdm.equipmentservice.model.dto.list.InspectionTicketListDto;
import com.mdm.equipmentservice.query.param.GetEquipmentsForInspectionQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.InspectionTicketService;
import com.mdm.equipmentservice.util.MessageUtil;
import jakarta.validation.Valid;
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
@RequestMapping("api/v1/equipments")
public class InspectionController {
    private final MessageUtil messageUtil;

    private final InspectionTicketService inspectionTicketService;

    private final PagedResourcesAssembler<EquipmentListInspectionDto> pagedResourcesAssembler;

    public InspectionController(
            MessageUtil messageUtil, InspectionTicketService inspectionTicketService, PagedResourcesAssembler<EquipmentListInspectionDto> pagedResourcesAssembler
    ) {
        this.messageUtil = messageUtil;
        this.inspectionTicketService = inspectionTicketService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping(value = {"/{equipmentId}/inspections"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse<InspectionTicketFullInfoDto>> createInspectionTicket(
            @RequestPart @Valid CreateInspectionTicketForm createInspectionTicketForm, @PathVariable Long equipmentId,
            @RequestPart(required = false) MultipartFile... attachments
    ) {
        InspectionTicketFullInfoDto inspectionFullInfoDto = inspectionTicketService.createInspectionTicket(createInspectionTicketForm,
                equipmentId,
                attachments
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>(inspectionFullInfoDto, 201));
    }

    @GetMapping("/inspections")
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<EquipmentListInspectionDto>>>> getAllEquipmentForInspection(
            GetEquipmentsForInspectionQueryParam getEquipmentsForInspectionQueryParam, Pageable pageable
    ) {
        Page<EquipmentListInspectionDto> allEquipmentsForInspection = inspectionTicketService.getAllEquipmentsForInspection(getEquipmentsForInspectionQueryParam,
                pageable
        );
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(pagedResourcesAssembler.toModel(allEquipmentsForInspection), 200));

    }



    @GetMapping("/{equipmentId}/inspections/{inspectionId}")
    public ResponseEntity<GenericResponse<InspectionTicketFullInfoDto>> getInspectionDetail(
            @PathVariable Long equipmentId, @PathVariable Long inspectionId
    ) {
        InspectionTicketFullInfoDto inspectionDetail = inspectionTicketService.getInspectionDetail(equipmentId, inspectionId);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(inspectionDetail, 200));
    }

    @PutMapping(value = "/{equipmentId}/inspections/{inspectionId}/accept")
    public ResponseEntity<GenericResponse<InspectionTicketFullInfoDto>> acceptInspectionTicket(
            @PathVariable Long equipmentId, @PathVariable Long inspectionId, @Valid @RequestBody AcceptInspectionTicketForm acceptInspectionTicketForm
    ) {
        InspectionTicketFullInfoDto inspectionTicketFullInfoDto = inspectionTicketService.acceptInspectionTicket(equipmentId,
                inspectionId,
                acceptInspectionTicketForm
        );
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(inspectionTicketFullInfoDto, 200));
    }

    @PutMapping(value = "/{equipmentId}/inspections/{inspectionId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse<InspectionTicketFullInfoDto>> updateInspectionTicket(
            @PathVariable Long equipmentId, @PathVariable Long inspectionId, @Valid @RequestPart UpdateInspectionTicketForm updateInspectionTicketForm,
            @RequestPart(required = false) MultipartFile... attachments
    ) {
        InspectionTicketFullInfoDto inspectionTicketFullInfoDto = inspectionTicketService.updateInspectionTicket(equipmentId,
                inspectionId,
                updateInspectionTicketForm,
                attachments
        );
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(inspectionTicketFullInfoDto, 200));
    }

    @GetMapping(value = "/{equipmentId}/inspections")
    public ResponseEntity<GenericResponse<Page<InspectionTicketListDto>>> getAllInspectionTicketsOfAnEquipment(
            @PathVariable Long equipmentId, Pageable pageable
    ) {
        Page<InspectionTicketListDto> allInspectionTicketsOfAnEquipment = inspectionTicketService.getAllInspectionTicketsOfAnEquipment(equipmentId,
                pageable
        );
        return ResponseEntity.status(200).body(new GenericResponse<>(allInspectionTicketsOfAnEquipment, 200));
    }
}
