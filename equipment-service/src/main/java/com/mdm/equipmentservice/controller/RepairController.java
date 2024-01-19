package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.model.dto.form.AcceptRepairTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateRepairTicketForm;
import com.mdm.equipmentservice.model.dto.form.UpdateRepairTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.RepairTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListRepairDto;
import com.mdm.equipmentservice.query.param.GetEquipmentsForRepairQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.RepairTicketService;
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
@RequestMapping(value = "/api/v1/equipments")
public class RepairController {


    private final RepairTicketService repairTicketService;

    private final PagedResourcesAssembler<EquipmentListRepairDto> pagedResourcesAssembler;

    @Autowired
    public RepairController(RepairTicketService repairTicketService,
                            PagedResourcesAssembler<EquipmentListRepairDto> equipmentListRepairDtoPagedResourcesAssembler) {
        this.repairTicketService = repairTicketService;
        this.pagedResourcesAssembler = equipmentListRepairDtoPagedResourcesAssembler;
    }

    @PostMapping(value = {"/{equipmentId}/repairs"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse<RepairTicketFullInfoDto>> createRepairTicket(@RequestPart @Valid CreateRepairTicketForm createRepairTicketForm,
                                                                                       @PathVariable Long equipmentId,
                                                                                       @RequestPart(required = false) MultipartFile... attachments) {
        RepairTicketFullInfoDto repairTicketFullInfoDto = repairTicketService.createRepairTicket(createRepairTicketForm, equipmentId, attachments);
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>(repairTicketFullInfoDto, 201));
    }

    @GetMapping("/{equipmentId}/repairs/{repairId}")
    public ResponseEntity<GenericResponse<RepairTicketFullInfoDto>> getRepairDetail(@PathVariable Long equipmentId, @PathVariable Long repairId) {
        RepairTicketFullInfoDto repairDetail = repairTicketService.getRepairDetail(equipmentId, repairId);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(repairDetail, 200));
    }

    @PutMapping(value = "/{equipmentId}/repairs/{repairId}/accept")
    public ResponseEntity<GenericResponse<RepairTicketFullInfoDto>> acceptRepairTicket(@PathVariable Long equipmentId, @PathVariable Long repairId,
                                                                                       @Valid @RequestBody AcceptRepairTicketForm acceptRepairTicketForm) {
        RepairTicketFullInfoDto repairTicketFullInfoDto = repairTicketService.acceptRepairTicket(equipmentId, repairId, acceptRepairTicketForm);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(repairTicketFullInfoDto, 200));
    }


    @GetMapping(value = "/repairs")
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<EquipmentListRepairDto>>>> getAllEquipmentsForRepair(
            GetEquipmentsForRepairQueryParam getEquipmentsForRepairQueryParam, Pageable pageable) {
        Page<EquipmentListRepairDto> allEquipmentsForRepair = repairTicketService.getAllEquipmentsForRepair(getEquipmentsForRepairQueryParam, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(pagedResourcesAssembler.toModel(allEquipmentsForRepair), 200));
    }

    @PutMapping(value = "/{equipmentId}/repairs/{repairTicketId}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse<RepairTicketFullInfoDto>> updateRepairTicket(@PathVariable Long repairTicketId,
                                                                                       @Valid @RequestPart UpdateRepairTicketForm updateRepairTicketForm,
                                                                                       @PathVariable Long equipmentId,
                                                                                       @RequestPart(required = false) MultipartFile... attachments) {
        RepairTicketFullInfoDto repairTicketFullInfoDto =
                repairTicketService.updateRepairTicket(repairTicketId, equipmentId, updateRepairTicketForm, attachments);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(repairTicketFullInfoDto, 200));
    }

    @PutMapping(value = "/{equipmentId}/repairs/{repairTicketId}/acceptance-testing", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse<Object>> acceptanceTesting(@PathVariable Long repairTicketId, @PathVariable Long equipmentId,
                                                                     @RequestPart(required = false) MultipartFile... attachments) {
        repairTicketService.acceptanceTesting(repairTicketId, equipmentId, attachments);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(null, 200));
    }
}
