package com.mdm.equipmentservice.service;
import com.mdm.equipmentservice.model.dto.base.SupplyUnitDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplyUnitService {

    @PreAuthorize("hasAuthority(\"SUPPLY.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    Page<SupplyUnitDto> getAllSupplyUnits(String keyword, Pageable pageable);

    @PreAuthorize("hasAnyAuthority(\"SUPPLY.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    SupplyUnitDto getSupplyUnit(Long id);
    @PreAuthorize("hasAuthority(\"SUPPLY.CREATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    SupplyUnitDto create(SupplyUnitDto upsertSupplyUnitDto);

    @PreAuthorize("hasAuthority(\"SUPPLY.UPDATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    SupplyUnitDto update(SupplyUnitDto upsertSupplyUnitDto, Long id);

    @PreAuthorize("hasAuthority(\"SUPPLY.DELETE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void deleteSupplyUnit(Long supplyUnitId);

    @PreAuthorize("hasAuthority(\"SUPPLY.DELETE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void deleteMultipleSupplyUnit(List<Long> supplyUnitIds);
}
