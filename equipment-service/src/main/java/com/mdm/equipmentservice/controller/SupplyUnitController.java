package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.model.dto.base.SupplyUnitDto;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.SupplyUnitService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/supply-units")
public class SupplyUnitController {

    private final SupplyUnitService supplyUnitService;

    private final PagedResourcesAssembler<SupplyUnitDto> pagedResourcesAssembler;


    public SupplyUnitController(SupplyUnitService supplyUnitService, PagedResourcesAssembler<SupplyUnitDto> pagedResourcesAssembler) {
        this.supplyUnitService = supplyUnitService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<SupplyUnitDto>>>> getAllSupplyUnits
            (@Param("keyword") String keyword, Pageable pageable) {
        PagedModel<EntityModel<SupplyUnitDto>> entityModels =
                pagedResourcesAssembler.toModel(supplyUnitService.getAllSupplyUnits(keyword, pageable));
        return ResponseEntity.ok(new GenericResponse<>(entityModels));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<SupplyUnitDto>> getSupplyUnit
            (@PathVariable("id") Long id) {
        SupplyUnitDto supplyUnitDto = supplyUnitService.getSupplyUnit(id);
        return ResponseEntity.ok(new GenericResponse<>(supplyUnitDto));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<SupplyUnitDto>> createSupplyUnit
            (@RequestBody SupplyUnitDto supplyUnitDto) {
        SupplyUnitDto createdSupplyUnitDto = supplyUnitService.create(supplyUnitDto);
        return new ResponseEntity<>(new GenericResponse<>(createdSupplyUnitDto, 201), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<SupplyUnitDto>> updateSupplyUnit
            (@PathVariable("id") Long id, @RequestBody SupplyUnitDto supplyUnitDto) {
        SupplyUnitDto updatedSupplyUnitDto = supplyUnitService.update(supplyUnitDto, id);
        return ResponseEntity.ok(new GenericResponse<>(updatedSupplyUnitDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Object>> deleteSupplyUnit
            (@PathVariable("id") Long id) {
        supplyUnitService.deleteSupplyUnit(id);
        return new ResponseEntity<>(new GenericResponse<>(null, 204), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete-multiple")
    public ResponseEntity<GenericResponse<Object>> deleteMultipleSupplyUnits
            (@RequestBody List<Long> ids) {
        supplyUnitService.deleteMultipleSupplyUnit(ids);
        return new ResponseEntity<>(new GenericResponse<>(null, 204), HttpStatus.NO_CONTENT);
    }

}
