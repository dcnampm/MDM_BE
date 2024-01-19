package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.base.EquipmentUnitDto;
import com.mdm.equipmentservice.model.dto.form.UpsertEquipmentUnitForm;
import com.mdm.equipmentservice.query.param.GetEquipmentUnitsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface EquipmentUnitService {
    @PreAuthorize("hasAuthority(\"EQUIPMENT_UNIT.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    EquipmentUnitDto getEquipmentUnitById(Long equipmentUnitId);

    @PreAuthorize("hasAuthority(\"EQUIPMENT_UNIT.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    Page<EquipmentUnitDto> getEquipmentUnits(GetEquipmentUnitsQueryParam getEquipmentUnitsQueryParam, Pageable pageable);

    @PreAuthorize("hasAuthority(\"EQUIPMENT_UNIT.CREATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    EquipmentUnitDto createEquipmentUnit(UpsertEquipmentUnitForm upsertEquipmentUnitForm);

    @PreAuthorize("hasAuthority(\"EQUIPMENT_UNIT.UPDATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    EquipmentUnitDto updateEquipmentUnit(Long equipmentUnitId, UpsertEquipmentUnitForm upsertEquipmentUnitForm);

    @PreAuthorize("hasAuthority(\"EQUIPMENT_UNIT.DELETE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void deleteEquipmentUnit(Long equipmentUnitId);
}
