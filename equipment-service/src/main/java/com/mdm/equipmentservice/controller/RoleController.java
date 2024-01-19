package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.model.dto.form.UpsertRoleForm;
import com.mdm.equipmentservice.model.dto.fullinfo.RoleFullInfoDto;
import com.mdm.equipmentservice.query.param.GetRolesQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    private final PagedResourcesAssembler<RoleFullInfoDto> pagedResourcesAssembler;


    public RoleController(RoleService roleService, PagedResourcesAssembler<RoleFullInfoDto> pagedResourcesAssembler) {
        this.roleService = roleService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }


    @GetMapping
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<RoleFullInfoDto>>>> getRoles(
            GetRolesQueryParam getRolesQueryParam, Pageable pageable
    ) {
        PagedModel<EntityModel<RoleFullInfoDto>> entityModels = pagedResourcesAssembler.toModel(
                roleService.getRoles(
                        getRolesQueryParam,
                        pageable
                ));
        return ResponseEntity.ok(new GenericResponse<>(entityModels));
    }


    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<RoleFullInfoDto>> getRole(@PathVariable(name = "id") Long roleId) {
        RoleFullInfoDto roleDto = roleService.getRoleById(roleId);
        return ResponseEntity.ok(new GenericResponse<>(roleDto));
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<RoleFullInfoDto>> createRole(
            @RequestBody @Valid UpsertRoleForm keycloakUpsertRoleForm
    ) {
        RoleFullInfoDto roleDto = roleService.createRole(keycloakUpsertRoleForm);
        return new ResponseEntity<>(new GenericResponse<>(roleDto, 201), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<RoleFullInfoDto>> updateRole(
            @PathVariable(name = "id") Long roleId, @RequestBody @Valid UpsertRoleForm keycloakUpsertRoleForm
    ) {
        RoleFullInfoDto roleDto = roleService.updateRole(
                roleId,
                keycloakUpsertRoleForm
        );
        return ResponseEntity.ok(new GenericResponse<>(roleDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Object>> deleteMapping(@PathVariable(name = "id") Long roleId) {
        roleService.deleteRole(roleId);
        return new ResponseEntity<>(new GenericResponse<>(null, 200), HttpStatus.OK);
    }

    @PostMapping("permissions")
    public void createPermissions() {
        roleService.createPermissions();
    }
}
