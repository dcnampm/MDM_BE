package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.form.UpsertEquipmentGroupForm;
import com.mdm.equipmentservice.model.dto.fullinfo.EquipmentGroupFullInfoDto;
import com.mdm.equipmentservice.query.param.GetEquipmentGroupsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface EquipmentGroupService {
    @PreAuthorize("hasAuthority(\"EQUIPMENT_GROUP.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    Page<EquipmentGroupFullInfoDto> getEquipmentGroups(GetEquipmentGroupsQueryParam getEquipmentGroupsQueryParam, Pageable pageable);

    @PreAuthorize("hasAuthority(\"EQUIPMENT_GROUP.CREATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    EquipmentGroupFullInfoDto createEquipmentGroup(UpsertEquipmentGroupForm upsertEquipmentGroupForm);

    @PreAuthorize("hasAuthority(\"EQUIPMENT_GROUP.UPDATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    EquipmentGroupFullInfoDto updateEquipmentGroup(Long equipmentGroupId, UpsertEquipmentGroupForm upsertEquipmentGroupForm);

    @PreAuthorize("hasAuthority(\"EQUIPMENT_GROUP.DELETE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void deleteEquipmentGroup(Long equipmentGroupId);

    @PreAuthorize("hasAuthority(\"EQUIPMENT_GROUP.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    EquipmentGroupFullInfoDto getEquipmentGroupById(Long equipmentGroupId);
}
