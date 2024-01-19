package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.form.UpsertRoleForm;
import com.mdm.equipmentservice.model.dto.fullinfo.RoleFullInfoDto;
import com.mdm.equipmentservice.query.param.GetRolesQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface RoleService {

    @PreAuthorize("hasAuthority(\"ROLE.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    Page<RoleFullInfoDto> getRoles(GetRolesQueryParam getRolesQueryParam, Pageable pageable);

    @PreAuthorize("hasAuthority(\"ROLE.CREATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    RoleFullInfoDto createRole(UpsertRoleForm upsertRoleForm);

    @PreAuthorize("hasAuthority(\"ROLE.UPDATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    RoleFullInfoDto updateRole(Long roleId, UpsertRoleForm upsertRoleForm);

    @PreAuthorize("hasAuthority(\"ROLE.DELETE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void deleteRole(Long roleId);

    @PreAuthorize("hasAuthority(\"ROLE.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    RoleFullInfoDto getRoleById(Long roleId);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void createPermissions();
}
