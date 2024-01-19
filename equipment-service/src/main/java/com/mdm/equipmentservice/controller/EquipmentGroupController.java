package com.mdm.equipmentservice.controller;


import com.mdm.equipmentservice.model.dto.form.UpsertEquipmentGroupForm;
import com.mdm.equipmentservice.model.dto.fullinfo.EquipmentGroupFullInfoDto;
import com.mdm.equipmentservice.query.param.GetEquipmentGroupsQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.EquipmentGroupService;
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
@RequestMapping("api/v1/equipment-groups")
public class EquipmentGroupController {
    private final EquipmentGroupService equipmentGroupService;

    private final PagedResourcesAssembler<EquipmentGroupFullInfoDto> equipmentGroupDtoPagedResourcesAssembler;

    public EquipmentGroupController(
            EquipmentGroupService equipmentGroupService,
            PagedResourcesAssembler<EquipmentGroupFullInfoDto> equipmentGroupDtoPagedResourcesAssembler
    ) {
        this.equipmentGroupService = equipmentGroupService;
        this.equipmentGroupDtoPagedResourcesAssembler = equipmentGroupDtoPagedResourcesAssembler;
    }


    @GetMapping
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<EquipmentGroupFullInfoDto>>>> getEquipmentGroups(
            GetEquipmentGroupsQueryParam getEquipmentGroupsQueryParam, Pageable pageable
    ) {
        PagedModel<EntityModel<EquipmentGroupFullInfoDto>> entityModels = equipmentGroupDtoPagedResourcesAssembler.toModel(
                equipmentGroupService.getEquipmentGroups(
                        getEquipmentGroupsQueryParam,
                        pageable
                ));
        return ResponseEntity.ok(new GenericResponse<>(entityModels));
    }


    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<EquipmentGroupFullInfoDto>> getEquipmentGroup(@PathVariable(name = "id") Long equipmentGroupId) {
        EquipmentGroupFullInfoDto equipmentGroupDto = equipmentGroupService.getEquipmentGroupById(equipmentGroupId);
        return ResponseEntity.ok(new GenericResponse<>(equipmentGroupDto));
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<EquipmentGroupFullInfoDto>> createEquipmentGroup(
            @RequestBody @Valid UpsertEquipmentGroupForm upsertEquipmentGroupForm
    ) {
        EquipmentGroupFullInfoDto equipmentGroupDto = equipmentGroupService.createEquipmentGroup(upsertEquipmentGroupForm);
        return new ResponseEntity<>(new GenericResponse<>(equipmentGroupDto, 201), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<EquipmentGroupFullInfoDto>> updateEquipmentGroup(
            @PathVariable(name = "id") Long equipmentGroupId, @RequestBody @Valid UpsertEquipmentGroupForm upsertEquipmentGroupForm
    ) {
        EquipmentGroupFullInfoDto equipmentGroupDto = equipmentGroupService.updateEquipmentGroup(
                equipmentGroupId,
                upsertEquipmentGroupForm
        );
        return ResponseEntity.ok(new GenericResponse<>(equipmentGroupDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Object>> deleteMapping(@PathVariable(name = "id") Long equipmentGroupId) {
        equipmentGroupService.deleteEquipmentGroup(equipmentGroupId);
        return new ResponseEntity<>(new GenericResponse<>(null, 200), HttpStatus.OK);
    }
}
