package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.form.UpsertDepartmentForm;
import com.mdm.equipmentservice.model.dto.fullinfo.DepartmentFullInfoDto;
import com.mdm.equipmentservice.query.param.GetDepartmentsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public interface DepartmentService {
    @PreAuthorize("hasAuthority(\"DEPARTMENT.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    DepartmentFullInfoDto getDepartmentById(Long departmentId);

    @PreAuthorize("hasAuthority(\"DEPARTMENT.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    Page<DepartmentFullInfoDto> getDepartments(GetDepartmentsQueryParam getDepartmentsQueryParam, Pageable pageable);

    @PreAuthorize("hasAuthority(\"DEPARTMENT.CREATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    DepartmentFullInfoDto createDepartment(UpsertDepartmentForm upsertDepartmentForm);

    @PreAuthorize("hasAuthority(\"DEPARTMENT.UPDATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    DepartmentFullInfoDto updateDepartment(Long departmentId, UpsertDepartmentForm upsertDepartmentForm);

    @PreAuthorize("hasAuthority(\"DEPARTMENT.DELETE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void deleteDepartment(Long departmentId);
}
