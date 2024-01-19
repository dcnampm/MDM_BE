package com.mdm.equipmentservice.controller;

import com.mdm.equipmentservice.model.dto.base.ProjectDto;
import com.mdm.equipmentservice.model.dto.form.UpsertProjectForm;
import com.mdm.equipmentservice.query.param.GetProjectsQueryParam;
import com.mdm.equipmentservice.response.GenericResponse;
import com.mdm.equipmentservice.service.ProjectService;
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
@RequestMapping(value = "/api/v1/projects")
public class ProjectController {


    private final ProjectService projectService;

    private final PagedResourcesAssembler<ProjectDto> pagedResourcesAssembler;

    @Autowired
    public ProjectController(
            ProjectService projectService, PagedResourcesAssembler<ProjectDto> pagedResourcesAssembler
    ) {
        this.projectService = projectService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<GenericResponse<PagedModel<EntityModel<ProjectDto>>>> getProjects(
            GetProjectsQueryParam getProjectsQueryParam, Pageable pageable
    ) {
        PagedModel<EntityModel<ProjectDto>> entityModels = pagedResourcesAssembler.toModel(projectService.getProjects(
                getProjectsQueryParam,
                pageable
        ));
        return ResponseEntity.ok(new GenericResponse<>(entityModels));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<ProjectDto>> getProject(@PathVariable(name = "id") Long projectId) {
        ProjectDto project = projectService.getProjectById(projectId);
        return ResponseEntity.ok(new GenericResponse<>(project));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<ProjectDto>> createProject(@RequestBody @Valid UpsertProjectForm upsertProjectForm) {
        ProjectDto projectFullInfoDto = projectService.createProject(upsertProjectForm);
        return new ResponseEntity<>(new GenericResponse<>(projectFullInfoDto, 201), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<ProjectDto>> updateProject(
            @PathVariable(name = "id") Long projectId, @RequestBody @Valid UpsertProjectForm upsertProjectForm
    ) {
        ProjectDto projectFullInfoDto = projectService.updateProject(projectId, upsertProjectForm);
        return ResponseEntity.ok(new GenericResponse<>(projectFullInfoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Object>> deleteProject(@PathVariable(name = "id") Long projectId) {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>(new GenericResponse<>(null, 200), HttpStatus.OK);
    }
}
