package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.dto.base.PermissionDto;
import com.mdm.equipmentservice.model.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface PermissionRepository extends ParentRepository<Permission, Long> {

    Set<Permission> findByNameInIgnoreCase(Collection<String> names);

    Set<Permission> findByIdIn(Collection<Long> ids);

}