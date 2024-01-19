package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.model.dto.base.EquipmentUnitDto;
import com.mdm.equipmentservice.model.dto.form.UpsertEquipmentUnitForm;
import com.mdm.equipmentservice.model.entity.EquipmentUnit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EquipmentUnitMapper {

    EquipmentUnitDto toDto(EquipmentUnit equipmentUnit);

    EquipmentUnit toEntity(UpsertEquipmentUnitForm upsertEquipmentUnitForm);

    EquipmentUnit partialUpdate(@MappingTarget EquipmentUnit equipmentUnit, UpsertEquipmentUnitForm upsertEquipmentUnitForm);

}