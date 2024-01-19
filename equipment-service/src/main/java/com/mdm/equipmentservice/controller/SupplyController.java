package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.model.dto.form.UpsertSupplyForm;
import com.mdm.equipmentservice.model.dto.fullinfo.SupplyFullInfoDto;
import com.mdm.equipmentservice.query.param.GetSupplyQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.SupplyService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/supplies")
public class SupplyController {

    @Autowired
    private SupplyService supplyService;

    @Autowired
    private PagedResourcesAssembler<SupplyFullInfoDto> pagedResourcesAssembler;


    @GetMapping("test")
    public String test() {
        return "test";
    }

    @GetMapping()
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<SupplyFullInfoDto>>>> getSupplies(GetSupplyQueryParam getSupplyQueryParam, Pageable pageable) {
        Page<SupplyFullInfoDto> page = supplyService.getAllSupplies(getSupplyQueryParam, pageable);
        return ResponseEntity.ok(new GenericResponse<>(pagedResourcesAssembler.toModel(page)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<SupplyFullInfoDto>> getSupply(@PathVariable("id") Long id) {
        SupplyFullInfoDto supplyDto = supplyService.getSupply(id);
        return ResponseEntity.ok(new GenericResponse<>(supplyDto));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<SupplyFullInfoDto>> createSupply(@RequestBody UpsertSupplyForm upsertSupplyForm) {
        SupplyFullInfoDto supplyDto = supplyService.create(upsertSupplyForm);
        return new ResponseEntity<>(new GenericResponse<>(supplyDto, 201), HttpStatus.CREATED);
    }

    @PostMapping(value = "/create-multiple", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<Object>> createMultipleSupply(List<UpsertSupplyForm> upsertSupplyForms) {
        supplyService.createMultipleSupply(upsertSupplyForms);
        return new ResponseEntity<>(new GenericResponse<>(null, 201), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<SupplyFullInfoDto>> updateSupply(
            @RequestBody UpsertSupplyForm upsertSupplyForm,
            @PathVariable("id") Long id
    ) {
        SupplyFullInfoDto supplyDto = supplyService.update(upsertSupplyForm, id);
        return ResponseEntity.ok(new GenericResponse<>(supplyDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Object>> deleteMapping(@PathVariable(name = "id") Long supplyId) {
        supplyService.deleteSupply(supplyId);
        return new ResponseEntity<>(new GenericResponse<>(null, 200), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete-multiple", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<Object>> deleteMultipleSupply(List<Long> supplyIds) {
        supplyService.deleteMultipleSupply(supplyIds);
        return new ResponseEntity<>(new GenericResponse<>(null, 200), HttpStatus.OK);
    }

}
