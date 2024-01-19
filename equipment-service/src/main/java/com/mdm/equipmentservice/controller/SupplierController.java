package com.mdm.equipmentservice.controller;


import com.mdm.equipmentservice.model.dto.form.UpsertSupplierForm;
import com.mdm.equipmentservice.model.dto.fullinfo.SupplierFullInfoDto;
import com.mdm.equipmentservice.query.param.GetSuppliersQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/api/v1/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    private final PagedResourcesAssembler<SupplierFullInfoDto> supplierDtoPagedResourcesAssembler;

    @Autowired
    public SupplierController(
            SupplierService supplierService, PagedResourcesAssembler<SupplierFullInfoDto> supplierDtoPagedResourcesAssembler
    ) {
        this.supplierService = supplierService;
        this.supplierDtoPagedResourcesAssembler = supplierDtoPagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<SupplierFullInfoDto>>>> getSuppliers(
            GetSuppliersQueryParam getSuppliersQueryParam, Pageable pageable
    ) {
        PagedModel<EntityModel<SupplierFullInfoDto>> entityModels = supplierDtoPagedResourcesAssembler.toModel(supplierService.getSuppliers(
                getSuppliersQueryParam,
                pageable
        ));
        return ResponseEntity.ok(new GenericResponse<>(entityModels));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<SupplierFullInfoDto>> getSupplier(@PathVariable(name = "id") Long supplierId) {
        SupplierFullInfoDto supplierDto = supplierService.getSupplierById(supplierId);
        return ResponseEntity.ok(new GenericResponse<>(supplierDto));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<SupplierFullInfoDto>> createSupplier(@RequestBody @Valid UpsertSupplierForm upsertSupplierForm) {
        SupplierFullInfoDto supplierDto = supplierService.createSupplier(upsertSupplierForm);
        return new ResponseEntity<>(new GenericResponse<>(supplierDto, 201), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<SupplierFullInfoDto>> updateSupplier(
            @PathVariable(name = "id") Long supplierId, @RequestBody @Valid UpsertSupplierForm upsertSupplierForm
    ) {
        SupplierFullInfoDto supplierDto = supplierService.updateSupplier(supplierId, upsertSupplierForm);
        return ResponseEntity.ok(new GenericResponse<>(supplierDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Object>> deleteSupplier(@PathVariable(name = "id") Long supplierId) {
        supplierService.deleteSupplier(supplierId);
        return new ResponseEntity<>(new GenericResponse<>(null, 204), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/delete-multiple", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<Object>> deleteMultiple(@RequestBody List<Long> supplierIds) {
        supplierService.deleteMultipleSupplier(supplierIds);
        return new ResponseEntity<>(new GenericResponse<>(null, 204), HttpStatus.NO_CONTENT);
    }


}
