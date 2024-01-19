package com.mdm.equipmentservice.controller;




import com.mdm.equipmentservice.model.dto.base.SupplyCategoryDto;
import com.mdm.equipmentservice.model.dto.form.UpsertSupplyCategoryForm;
import com.mdm.equipmentservice.query.param.GetSupplyCategoriesQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.SupplyCategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@Slf4j
@RequestMapping("/api/v1/supply-categories")
public class SupplyCategoryController {
    private final SupplyCategoryService supplyCategoryService;

    private final PagedResourcesAssembler<SupplyCategoryDto> supplyCategoryWithUserDtoPagedResourcesAssembler;

    @Autowired
    public SupplyCategoryController(
            SupplyCategoryService supplyCategoryService, PagedResourcesAssembler<SupplyCategoryDto> supplyCategoryWithUserDtoPagedResourcesAssembler
    ) {
        this.supplyCategoryService = supplyCategoryService;
        this.supplyCategoryWithUserDtoPagedResourcesAssembler = supplyCategoryWithUserDtoPagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<SupplyCategoryDto>>>> getSupplyCategories(
            GetSupplyCategoriesQueryParam getSupplyCategoriesQueryParam, Pageable pageable
    ) {
        PagedModel<EntityModel<SupplyCategoryDto>> entityModels = supplyCategoryWithUserDtoPagedResourcesAssembler.toModel(supplyCategoryService.getSupplyCategories(
                getSupplyCategoriesQueryParam,
                pageable
        ));
        return ResponseEntity.ok(new GenericResponse<>(entityModels));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<SupplyCategoryDto>> getSupplyCategory(@PathVariable(name = "id") Long supplyCategoryId) {
        SupplyCategoryDto supplyCategory = supplyCategoryService.getSupplyCategoryById(supplyCategoryId);
        return ResponseEntity.ok(new GenericResponse<>(supplyCategory));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<SupplyCategoryDto>> createSupplyCategory(@RequestBody @Valid UpsertSupplyCategoryForm upsertSupplyCategoryForm) {
        SupplyCategoryDto supplyCategoryFullInfoDto = supplyCategoryService.createSupplyCategory(upsertSupplyCategoryForm);
        return new ResponseEntity<>(new GenericResponse<>(supplyCategoryFullInfoDto, 201), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<SupplyCategoryDto>> updateSupplyCategory(
            @PathVariable(name = "id") Long supplyCategoryId, @RequestBody @Valid UpsertSupplyCategoryForm upsertSupplyCategoryForm
    ) {
        SupplyCategoryDto supplyCategoryFullInfoDto = supplyCategoryService.updateSupplyCategory(supplyCategoryId, upsertSupplyCategoryForm);
        return ResponseEntity.ok(new GenericResponse<>(supplyCategoryFullInfoDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Object>> deleteSupplyCategory(@PathVariable(name = "id") Long supplyCategoryId) {
        supplyCategoryService.deleteSupplyCategory(supplyCategoryId);
        return new ResponseEntity<>(new GenericResponse<>(null, 200), HttpStatus.OK);
    }

}
