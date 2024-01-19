package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.Project;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends ParentRepository<Project, Long> {
}