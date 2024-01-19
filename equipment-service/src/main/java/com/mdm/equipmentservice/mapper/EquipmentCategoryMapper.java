package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.model.dto.base.EquipmentCategoryDto;
import com.mdm.equipmentservice.model.dto.form.UpsertEquipmentCategoryForm;
import com.mdm.equipmentservice.model.dto.fullinfo.EquipmentCategoryFullInfoDto;
import com.mdm.equipmentservice.model.dto.list.EquipmentCategoryListDto;
import com.mdm.equipmentservice.model.entity.EquipmentCategory;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {IdToEntityMapper.class})
public abstract class EquipmentCategoryMapper {

    @Mapping(target = "group", source = "groupId")
    @Mapping(target = "id", ignore = true)
    public abstract EquipmentCategory toEntity(UpsertEquipmentCategoryForm upsertEquipmentCategoryForm);

    public abstract EquipmentCategoryFullInfoDto toFullInfoDto(EquipmentCategory equipmentCategory);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", source = "groupId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract EquipmentCategory partialUpdate(
            UpsertEquipmentCategoryForm equipmentCategoryWithRelationshipDto, @MappingTarget EquipmentCategory equipmentCategory
    );

    public abstract EquipmentCategoryDto toDto(EquipmentCategory equipmentCategory);

    public abstract EquipmentCategoryListDto toListDto(EquipmentCategory equipmentCategory);

    abstract EquipmentCategory toEntity(EquipmentCategoryDto equipmentCategoryDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract EquipmentCategory partialUpdate(
            EquipmentCategoryDto equipmentCategoryDto, @MappingTarget EquipmentCategory equipmentCategory);
}