package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.model.dto.base.EquipmentGroupDto;
import com.mdm.equipmentservice.model.dto.form.UpsertEquipmentGroupForm;
import com.mdm.equipmentservice.model.dto.fullinfo.EquipmentGroupFullInfoDto;
import com.mdm.equipmentservice.model.entity.EquipmentGroup;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EquipmentGroupMapper {

    EquipmentGroupDto toDto(EquipmentGroup equipmentGroup);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "equipmentCategories", ignore = true)
    EquipmentGroup toEntity(UpsertEquipmentGroupForm upsertEquipmentGroupForm);

    @Mapping(target = "equipmentCategories", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    EquipmentGroup partialUpdate(
            UpsertEquipmentGroupForm upsertEquipmentGroupForm, @MappingTarget EquipmentGroup equipmentGroup
    );

    EquipmentGroupFullInfoDto toFullInfoDto(EquipmentGroup equipmentGroup);

}