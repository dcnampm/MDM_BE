package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.model.dto.form.AcceptReportBrokenTicketForm;
import com.mdm.equipmentservice.model.dto.form.CreateReportBrokenTicketForm;
import com.mdm.equipmentservice.model.dto.fullinfo.ReportBrokenTicketFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentListReportBrokenDto;
import com.mdm.equipmentservice.query.param.GetEquipmentsForReportBrokenQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.ReportBrokenTicketService;
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
public class ReportBrokenController {

    private final ReportBrokenTicketService reportBrokenTicketService;

    private final PagedResourcesAssembler<EquipmentListReportBrokenDto> pagedResourcesAssembler;

    @Autowired
    public ReportBrokenController(ReportBrokenTicketService reportBrokenTicketService,
                                  PagedResourcesAssembler<EquipmentListReportBrokenDto> pagedResourcesAssembler) {
        this.reportBrokenTicketService = reportBrokenTicketService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping(value = {"/{equipmentId}/report-broken"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse<ReportBrokenTicketFullInfoDto>> createReportBrokenTicket(
            @RequestPart @Valid CreateReportBrokenTicketForm createReportBrokenTicketForm, @PathVariable Long equipmentId,
            @RequestPart(required = false) MultipartFile... attachments
    ) {
        ReportBrokenTicketFullInfoDto reportBrokenTicketFullInfoDto = reportBrokenTicketService.createReportBrokenTicket(
                createReportBrokenTicketForm,
                equipmentId,
                attachments
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>(reportBrokenTicketFullInfoDto, 201));
    }

    @GetMapping("/{equipmentId}/report-broken/{reportBrokenId}")
    public ResponseEntity<GenericResponse<ReportBrokenTicketFullInfoDto>> getReportBrokenDetail(
            @PathVariable Long equipmentId, @PathVariable Long reportBrokenId
    ) {
        ReportBrokenTicketFullInfoDto reportBrokenDetail = reportBrokenTicketService.getReportBrokenDetail(equipmentId, reportBrokenId);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(reportBrokenDetail, 200));
    }

    @PutMapping(value = "/{equipmentId}/report-broken/{reportBrokenId}/accept")
    public ResponseEntity<GenericResponse<ReportBrokenTicketFullInfoDto>> acceptReportBrokenTicket(
            @PathVariable Long equipmentId, @PathVariable Long reportBrokenId, @Valid @RequestBody AcceptReportBrokenTicketForm acceptReportBrokenTicketForm
    ) {
        ReportBrokenTicketFullInfoDto reportBrokenTicketFullInfoDto = reportBrokenTicketService.acceptReportBrokenTicket(
                equipmentId,
                reportBrokenId,
                acceptReportBrokenTicketForm
        );
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(reportBrokenTicketFullInfoDto, 200));
    }


    @GetMapping(value = "/report-broken")
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<EquipmentListReportBrokenDto>>>> getAllEquipmentsForReportBroken(
            GetEquipmentsForReportBrokenQueryParam getEquipmentsForReportBrokenQueryParam, Pageable pageable
    ) {
        Page<EquipmentListReportBrokenDto>
                allEquipmentsForReportBroken = reportBrokenTicketService.getAllEquipmentsForReportBroken(getEquipmentsForReportBrokenQueryParam, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(pagedResourcesAssembler.toModel(allEquipmentsForReportBroken), 200));
    }
}

