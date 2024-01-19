package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.model.dto.base.ProjectDto;
import com.mdm.equipmentservice.model.dto.form.UpsertProjectForm;
import com.mdm.equipmentservice.model.entity.Project;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectMapper {

    ProjectDto toDto(Project project);

    Project toEntity(UpsertProjectForm upsertProjectForm);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Project partialUpdate(UpsertProjectForm upsertProjectForm, @MappingTarget Project project);
}