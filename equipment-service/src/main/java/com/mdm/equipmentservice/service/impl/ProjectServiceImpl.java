package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.exception.ResourceNotFoundException;
import com.mdm.equipmentservice.mapper.ProjectMapper;
import com.mdm.equipmentservice.model.dto.base.ProjectDto;
import com.mdm.equipmentservice.model.dto.form.UpsertProjectForm;
import com.mdm.equipmentservice.model.entity.Project;
import com.mdm.equipmentservice.model.repository.ProjectRepository;
import com.mdm.equipmentservice.query.param.GetProjectsQueryParam;
import com.mdm.equipmentservice.query.predicate.QueryProjectPredicate;
import com.mdm.equipmentservice.service.ProjectService;
import com.mdm.equipmentservice.util.MessageUtil;
import com.mdm.equipmentservice.util.UniqueValidationUtil;
import com.mdm.equipmentservice.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    private final MessageUtil messageUtil;

    private final ValidationUtil validationUtil;

    private final UniqueValidationUtil uniqueValidationUtil;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, MessageUtil messageUtil,
                              ValidationUtil validationUtil, UniqueValidationUtil uniqueValidationUtil) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.messageUtil = messageUtil;
        this.validationUtil = validationUtil;
        this.uniqueValidationUtil = uniqueValidationUtil;
    }

    @Override
    public ProjectDto getProjectById(Long projectId) {
        validationUtil.validateNotFound(projectId, projectRepository, messageUtil.getMessage("projectNotFound"));
        Project project = projectRepository.findById(projectId).orElse(null);
        return projectMapper.toDto(project);
    }

    @Override
    public Page<ProjectDto> getProjects(GetProjectsQueryParam getProjectsQueryParam, Pageable pageable) {
        return projectRepository.findAll(QueryProjectPredicate.getProjectsPredicate(getProjectsQueryParam), pageable)
                .map(projectMapper::toDto);
    }

    @Override
    public ProjectDto createProject(UpsertProjectForm upsertProjectForm) {
        Project projectToBeCreated = projectMapper.toEntity(upsertProjectForm);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                projectToBeCreated,
                projectRepository,
                messageUtil.getMessage("projectUniquenessValidationFailed")
        );
        projectToBeCreated = projectRepository.save(projectToBeCreated);
        return projectMapper.toDto(projectToBeCreated);
    }

    @Override
    public ProjectDto updateProject(Long projectId, UpsertProjectForm upsertProjectForm) {
        Project projectToBeUpdated = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("projectNotFound")));
        projectToBeUpdated = projectMapper.partialUpdate(upsertProjectForm, projectToBeUpdated);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                projectToBeUpdated,
                projectRepository,
                messageUtil.getMessage("projectUniquenessValidationFailed")
        );
        projectToBeUpdated = projectRepository.save(projectToBeUpdated);
        return projectMapper.toDto(projectToBeUpdated);
    }

    @Override
    @Transactional
    public void deleteProject(Long projectId) {
        validationUtil.validateNotFound(projectId, projectRepository, messageUtil.getMessage("projectNotFound"));
        projectRepository.deleteById(projectId);
    }

}
