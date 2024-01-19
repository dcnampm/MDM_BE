package com.mdm.equipmentservice.service;


import com.mdm.equipmentservice.model.dto.form.UpsertSupplierForm;
import com.mdm.equipmentservice.model.dto.fullinfo.SupplierFullInfoDto;
import com.mdm.equipmentservice.query.param.GetSuppliersQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplierService {
    @PreAuthorize("hasAuthority(\"SUPPLIER.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    Page<SupplierFullInfoDto> getSuppliers(GetSuppliersQueryParam getSuppliersQueryParam, Pageable pageable);

    @PreAuthorize("hasAuthority(\"SUPPLIER.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    SupplierFullInfoDto getSupplierById(Long supplierId);

    @PreAuthorize("hasAuthority(\"SUPPLIER.CREATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    SupplierFullInfoDto createSupplier(UpsertSupplierForm upsertSupplierForm);

    @PreAuthorize("hasAuthority(\"SUPPLIER.UPDATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    SupplierFullInfoDto updateSupplier(Long supplierId, UpsertSupplierForm upsertSupplierForm);

    @PreAuthorize("hasAuthority(\"SUPPLIER.DELETE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void deleteSupplier(Long supplierId);

    @PreAuthorize("hasAuthority(\"SUPPLIER.CREATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void deleteMultipleSupplier(List<Long> supplierIds);
}
