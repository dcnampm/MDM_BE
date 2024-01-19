package com.mdm.equipmentservice.service;

import com.mdm.equipmentservice.model.dto.form.UpsertEquipmentCategoryForm;
import com.mdm.equipmentservice.model.dto.fullinfo.EquipmentCategoryFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentCategoryListDto;
import com.mdm.equipmentservice.query.param.GetEquipmentCategoriesQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


@Service
public interface EquipmentCategoryService {
    @PreAuthorize("hasAuthority(\"EQUIPMENT_CATEGORY.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    Page<EquipmentCategoryListDto> getEquipmentCategories(GetEquipmentCategoriesQueryParam getEquipmentCategoriesQueryParam, Pageable pageable);

    @PreAuthorize("hasAuthority(\"EQUIPMENT_CATEGORY.READ\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    EquipmentCategoryFullInfoDto getEquipmentCategoryById(Long equipmentCategoryId);

    @PreAuthorize("hasAuthority(\"EQUIPMENT_CATEGORY.CREATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    EquipmentCategoryFullInfoDto createEquipmentCategory(UpsertEquipmentCategoryForm upsertEquipmentCategoryForm);

    @PreAuthorize("hasAuthority(\"EQUIPMENT_CATEGORY.UPDATE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    EquipmentCategoryFullInfoDto updateEquipmentCategory(Long equipmentCategoryId, UpsertEquipmentCategoryForm upsertEquipmentCategoryForm);

    @PreAuthorize("hasAuthority(\"EQUIPMENT_CATEGORY.DELETE\") or hasAnyRole('ROLE_ADMIN','ROLE_TPVT')")
    void deleteEquipmentCategory(Long equipmentCategoryId);

}
