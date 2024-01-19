package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.form.UpsertSupplyForm;
import com.mdm.equipmentservice.model.dto.fullinfo.SupplyFullInfoDto;
import com.mdm.equipmentservice.query.param.GetSupplyQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplyService {
    @PreAuthorize("hasAuthority(\"SUPPLY.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    Page<SupplyFullInfoDto> getAllSupplies(GetSupplyQueryParam getSupplyQueryParam, Pageable pageable);

    @PreAuthorize("hasAuthority(\"SUPPLY.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    SupplyFullInfoDto getSupply(Long id);

    @PreAuthorize("hasAuthority(\"SUPPLY.CREATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    SupplyFullInfoDto create(UpsertSupplyForm upsertSupplyForm);

    @PreAuthorize("hasAuthority(\"SUPPLY.UPDATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    SupplyFullInfoDto update(UpsertSupplyForm upsertSupplyForm, Long id);

    @PreAuthorize("hasAuthority(\"SUPPLY.DELETE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void deleteSupply(Long supplyId);

    @PreAuthorize("hasAuthority(\"SUPPLY.CREATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void createMultipleSupply(List<UpsertSupplyForm> upsertSupplyForms);

    @PreAuthorize("hasAuthority(\"SUPPLY.DELETE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void deleteMultipleSupply(List<Long> supplyIds);
}
