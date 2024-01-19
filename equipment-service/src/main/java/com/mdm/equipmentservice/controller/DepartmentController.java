package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.model.dto.form.UpsertDepartmentForm;
import com.mdm.equipmentservice.model.dto.fullinfo.DepartmentFullInfoDto;
import com.mdm.equipmentservice.query.param.GetDepartmentsQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.DepartmentService;
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
@RequestMapping("/api/v1/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    private final PagedResourcesAssembler<DepartmentFullInfoDto> departmentWithUserDtoPagedResourcesAssembler;

    @Autowired
    public DepartmentController(
            DepartmentService departmentService, PagedResourcesAssembler<DepartmentFullInfoDto> departmentWithUserDtoPagedResourcesAssembler
    ) {
        this.departmentService = departmentService;
        this.departmentWithUserDtoPagedResourcesAssembler = departmentWithUserDtoPagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<DepartmentFullInfoDto>>>> getDepartments(
            GetDepartmentsQueryParam getDepartmentsQueryParam, Pageable pageable
    ) {
        PagedModel<EntityModel<DepartmentFullInfoDto>> entityModels = departmentWithUserDtoPagedResourcesAssembler.toModel(departmentService.getDepartments(
                getDepartmentsQueryParam,
                pageable
        ));
        return ResponseEntity.ok(new GenericResponse<>(entityModels));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<DepartmentFullInfoDto>> getDepartment(@PathVariable(name = "id") Long departmentId) {
        DepartmentFullInfoDto department = departmentService.getDepartmentById(departmentId);
        return ResponseEntity.ok(new GenericResponse<>(department));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<DepartmentFullInfoDto>> createDepartment(@RequestBody @Valid UpsertDepartmentForm upsertDepartmentForm) {
        DepartmentFullInfoDto departmentFullInfoDto = departmentService.createDepartment(upsertDepartmentForm);
        return new ResponseEntity<>(new GenericResponse<>(departmentFullInfoDto, 201), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<DepartmentFullInfoDto>> updateDepartment(
            @PathVariable(name = "id") Long departmentId, @RequestBody @Valid UpsertDepartmentForm upsertDepartmentForm
    ) {
        DepartmentFullInfoDto departmentFullInfoDto = departmentService.updateDepartment(departmentId, upsertDepartmentForm);
        return ResponseEntity.ok(new GenericResponse<>(departmentFullInfoDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Object>> deleteDepartment(@PathVariable(name = "id") Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return new ResponseEntity<>(new GenericResponse<>(null, 200), HttpStatus.OK);
    }

}
