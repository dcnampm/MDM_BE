package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.model.dto.form.UpsertEquipmentCategoryForm;
import com.mdm.equipmentservice.model.dto.fullinfo.EquipmentCategoryFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentCategoryListDto;
import com.mdm.equipmentservice.query.param.GetEquipmentCategoriesQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.EquipmentCategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/v1/equipment-categories")
public class EquipmentCategoryController {
    private final EquipmentCategoryService equipmentCategoryService;

    private final PagedResourcesAssembler<EquipmentCategoryListDto> equipmentCategoryDtoPagedResourcesAssembler;

    public EquipmentCategoryController(
            EquipmentCategoryService equipmentCategoryService,
            PagedResourcesAssembler<EquipmentCategoryListDto> equipmentCategoryDtoPagedResourcesAssembler
    ) {
        this.equipmentCategoryService = equipmentCategoryService;
        this.equipmentCategoryDtoPagedResourcesAssembler = equipmentCategoryDtoPagedResourcesAssembler;
    }


    @GetMapping
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<EquipmentCategoryListDto>>>> getEquipmentCategories(
            GetEquipmentCategoriesQueryParam getEquipmentCategoriesQueryParam, Pageable pageable
    ) {
        PagedModel<EntityModel<EquipmentCategoryListDto>> entityModels = equipmentCategoryDtoPagedResourcesAssembler.toModel(
                equipmentCategoryService.getEquipmentCategories(
                        getEquipmentCategoriesQueryParam,
                        pageable
                ));
        return ResponseEntity.ok(new GenericResponse<>(entityModels));
    }


    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<EquipmentCategoryFullInfoDto>> getEquipmentCategory(@PathVariable(name = "id") Long equipmentCategoryId) {
        EquipmentCategoryFullInfoDto equipmentCategoryDto = equipmentCategoryService.getEquipmentCategoryById(equipmentCategoryId);
        return ResponseEntity.ok(new GenericResponse<>(equipmentCategoryDto));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<EquipmentCategoryFullInfoDto>> createEquipmentCategory(
            @RequestBody @Valid UpsertEquipmentCategoryForm upsertEquipmentCategoryForm
    ) {
        EquipmentCategoryFullInfoDto equipmentCategoryDto = equipmentCategoryService.createEquipmentCategory(upsertEquipmentCategoryForm);
        return new ResponseEntity<>(new GenericResponse<>(equipmentCategoryDto, 201), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<EquipmentCategoryFullInfoDto>> updateEquipmentCategory(
            @PathVariable(name = "id") Long equipmentCategoryId, @RequestBody @Valid UpsertEquipmentCategoryForm upsertEquipmentCategoryForm
    ) {
        EquipmentCategoryFullInfoDto equipmentCategoryDto = equipmentCategoryService.updateEquipmentCategory(
                equipmentCategoryId,
                upsertEquipmentCategoryForm
        );
        return ResponseEntity.ok(new GenericResponse<>(equipmentCategoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Object>> deleteMapping(@PathVariable(name = "id") Long equipmentCategoryId) {
        equipmentCategoryService.deleteEquipmentCategory(equipmentCategoryId);
        return new ResponseEntity<>(new GenericResponse<>(null, 200), HttpStatus.OK);
    }

}
