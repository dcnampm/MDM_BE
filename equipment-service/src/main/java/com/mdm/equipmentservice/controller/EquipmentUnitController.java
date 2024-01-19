package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.model.dto.base.EquipmentUnitDto;
import com.mdm.equipmentservice.model.dto.form.UpsertEquipmentUnitForm;
import com.mdm.equipmentservice.query.param.GetEquipmentUnitsQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.EquipmentUnitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/equipment-units")
public class EquipmentUnitController {

    private final EquipmentUnitService equipmentUnitService;

    private final PagedResourcesAssembler<EquipmentUnitDto> pagedResourcesAssembler;

    @Autowired
    public EquipmentUnitController(
            EquipmentUnitService equipmentUnitService, PagedResourcesAssembler<EquipmentUnitDto> pagedResourcesAssembler
    ) {
        this.equipmentUnitService = equipmentUnitService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<EquipmentUnitDto>>>> getEquipmentUnits(
            GetEquipmentUnitsQueryParam getEquipmentUnitsQueryParam, Pageable pageable
    ) {
        PagedModel<EntityModel<EquipmentUnitDto>> entityModels = pagedResourcesAssembler.toModel(equipmentUnitService.getEquipmentUnits(
                getEquipmentUnitsQueryParam,
                pageable
        ));
        return ResponseEntity.ok(new GenericResponse<>(entityModels));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<EquipmentUnitDto>> getEquipmentUnit(@PathVariable(name = "id") Long equipmentUnitId) {
        EquipmentUnitDto equipmentUnit = equipmentUnitService.getEquipmentUnitById(equipmentUnitId);
        return ResponseEntity.ok(new GenericResponse<>(equipmentUnit));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<EquipmentUnitDto>> createEquipmentUnit(@RequestBody @Valid UpsertEquipmentUnitForm upsertEquipmentUnitForm) {
        EquipmentUnitDto equipmentUnitFullInfoDto = equipmentUnitService.createEquipmentUnit(upsertEquipmentUnitForm);
        return new ResponseEntity<>(new GenericResponse<>(equipmentUnitFullInfoDto, 201), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<EquipmentUnitDto>> updateEquipmentUnit(
            @PathVariable(name = "id") Long equipmentUnitId, @RequestBody @Valid UpsertEquipmentUnitForm upsertEquipmentUnitForm
    ) {
        EquipmentUnitDto equipmentUnitFullInfoDto = equipmentUnitService.updateEquipmentUnit(equipmentUnitId, upsertEquipmentUnitForm);
        return ResponseEntity.ok(new GenericResponse<>(equipmentUnitFullInfoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Object>> deleteEquipmentUnit(@PathVariable(name = "id") Long equipmentUnitId) {
        equipmentUnitService.deleteEquipmentUnit(equipmentUnitId);
        return new ResponseEntity<>(new GenericResponse<>(null, 200), HttpStatus.OK);
    }

}
