package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.base.SupplyCategoryDto;
import com.mdm.equipmentservice.model.dto.form.UpsertSupplyCategoryForm;
import com.mdm.equipmentservice.query.param.GetSupplyCategoriesQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface SupplyCategoryService {
    @PreAuthorize("hasAuthority(\"SUPPLY.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    SupplyCategoryDto getSupplyCategoryById(Long supplyCategoryId);

    @PreAuthorize("hasAuthority(\"SUPPLY.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    Page<SupplyCategoryDto> getSupplyCategories(GetSupplyCategoriesQueryParam getsupplyCategoriesQueryParam, Pageable pageable);

    @PreAuthorize("hasAuthority(\"SUPPLY.CREATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    SupplyCategoryDto createSupplyCategory(UpsertSupplyCategoryForm upsertSupplyCategoryForm);

    @PreAuthorize("hasAuthority(\"SUPPLY.UPDATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    SupplyCategoryDto updateSupplyCategory(Long supplyCategoryId, UpsertSupplyCategoryForm upsertSupplyCategoryForm);

    @PreAuthorize("hasAuthority(\"SUPPLY.DELETE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void deleteSupplyCategory(Long supplyCategoryId);
}
