package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.model.dto.form.AcceptMaintenanceTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateMaintenanceTicketForm;
import com.mdm.equipmentservice.model.dto.form.UpdateMaintenanceTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.MaintenanceTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListMaintenanceDto;
import com.mdm.equipmentservice.model.dto.list.MaintenanceTicketListDto;
import com.mdm.equipmentservice.query.param.GetEquipmentsForMaintenanceQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.MaintenanceTicketService;
import com.mdm.equipmentservice.util.MessageUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("api/v1/equipments")
public class MaintenanceController {
    private final MessageUtil messageUtil;

    private final MaintenanceTicketService maintenanceTicketService;

    private final PagedResourcesAssembler<EquipmentListMaintenanceDto> pagedResourcesAssembler;

    public MaintenanceController(
            MessageUtil messageUtil, MaintenanceTicketService maintenanceTicketService,
            PagedResourcesAssembler<EquipmentListMaintenanceDto> pagedResourcesAssembler
    ) {
        this.messageUtil = messageUtil;
        this.maintenanceTicketService = maintenanceTicketService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping(value = {"/{equipmentId}/maintenances"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse<MaintenanceTicketFullInfoDto>> createMaintenanceTicket(
            @RequestPart @Valid CreateMaintenanceTicketForm createMaintenanceTicketForm, @PathVariable Long equipmentId,
            @RequestPart(required = false) MultipartFile... attachments
    ) {
        MaintenanceTicketFullInfoDto maintenanceTicketFullInfoDto = maintenanceTicketService.createMaintenanceTicket(createMaintenanceTicketForm,
                equipmentId,
                attachments
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>(maintenanceTicketFullInfoDto, 201));
    }

    @GetMapping("/{equipmentId}/maintenances/{maintenanceId}")
    public ResponseEntity<GenericResponse<MaintenanceTicketFullInfoDto>> getMaintenanceDetail(
            @PathVariable Long equipmentId, @PathVariable Long maintenanceId
    ) {
        MaintenanceTicketFullInfoDto maintenanceDetail = maintenanceTicketService.getMaintenanceDetail(equipmentId, maintenanceId);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(maintenanceDetail, 200));
    }

    @PutMapping(value = "/{equipmentId}/maintenances/{maintenanceId}/accept")
    public ResponseEntity<GenericResponse<MaintenanceTicketFullInfoDto>> acceptMaintenanceTicket(
            @PathVariable Long equipmentId, @PathVariable Long maintenanceId, @Valid @RequestBody AcceptMaintenanceTicketForm acceptMaintenanceTicketForm
    ) {
        MaintenanceTicketFullInfoDto maintenanceTicketFullInfoDto = maintenanceTicketService.acceptMaintenanceTicket(equipmentId,
                maintenanceId,
                acceptMaintenanceTicketForm
        );
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(maintenanceTicketFullInfoDto, 200));
    }

    @PutMapping(value = "/{equipmentId}/maintenances/{maintenanceId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse<MaintenanceTicketFullInfoDto>> updateMaintenanceTicket(
            @PathVariable Long equipmentId, @PathVariable Long maintenanceId, @Valid @RequestPart UpdateMaintenanceTicketForm updateMaintenanceTicketForm,
            @RequestPart(required = false) MultipartFile... attachments
    ) {
        MaintenanceTicketFullInfoDto maintenanceTicketFullInfoDto = maintenanceTicketService.updateMaintenanceTicket(equipmentId,
                maintenanceId,
                updateMaintenanceTicketForm,
                attachments
        );
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(maintenanceTicketFullInfoDto, 200));
    }

    @GetMapping(value = "/maintenances")
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<EquipmentListMaintenanceDto>>>> getAllEquipmentsForMaintenance(
            GetEquipmentsForMaintenanceQueryParam getEquipmentsForMaintenanceQueryParam, Pageable pageable
    ) {
        Page<EquipmentListMaintenanceDto> allEquipmentsForMaintenance = maintenanceTicketService.getAllEquipmentsForMaintenance(getEquipmentsForMaintenanceQueryParam, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(pagedResourcesAssembler.toModel(allEquipmentsForMaintenance), 200));
    }

    @GetMapping(value = "/{equipmentId}/maintenances")
    public ResponseEntity<GenericResponse<Page<MaintenanceTicketListDto>>> getAllMaintenanceTicketsOfAnEquipment(
            @PathVariable Long equipmentId, Pageable pageable
    ) {
        Page<MaintenanceTicketListDto> allMaintenanceTicketsOfAnEquipment = maintenanceTicketService.getAllMaintenanceTicketsOfAnEquipment(equipmentId,
                pageable
        );
        return ResponseEntity.status(200).body(new GenericResponse<>(allMaintenanceTicketsOfAnEquipment, 200));
    }
}
