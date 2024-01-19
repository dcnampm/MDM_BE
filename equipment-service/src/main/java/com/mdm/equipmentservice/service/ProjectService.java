package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.base.ProjectDto;
import com.mdm.equipmentservice.model.dto.form.UpsertProjectForm;
import com.mdm.equipmentservice.query.param.GetProjectsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface ProjectService {
    @PreAuthorize("hasAuthority(\"PROJECT.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    ProjectDto getProjectById(Long projectId);

    @PreAuthorize("hasAuthority(\"PROJECT.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    Page<ProjectDto> getProjects(GetProjectsQueryParam getProjectsQueryParam, Pageable pageable);

    @PreAuthorize("hasAuthority(\"PROJECT.CREATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    ProjectDto createProject(UpsertProjectForm upsertProjectForm);

    @PreAuthorize("hasAuthority(\"PROJECT.UPDATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    ProjectDto updateProject(Long projectId, UpsertProjectForm upsertProjectForm);

    @PreAuthorize("hasAuthority(\"PROJECT.DELETE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void deleteProject(Long projectId);
}
