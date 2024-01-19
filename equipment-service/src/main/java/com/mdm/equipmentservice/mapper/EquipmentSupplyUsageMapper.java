package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.model.dto.base.EquipmentSupplyUsageDto;
import com.mdm.equipmentservice.model.dto.form.AttachSupplyForm;
import com.mdm.equipmentservice.model.entity.EquipmentSupplyUsage;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {IdToEntityMapper.class})
public interface EquipmentSupplyUsageMapper {

    EquipmentSupplyUsage toEntity(EquipmentSupplyUsageDto equipmentSupplyUsageDto);

    EquipmentSupplyUsageDto toDto(EquipmentSupplyUsage equipmentSupplyUsage);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "equipment", source = "equipmentId")
    @Mapping(target = "supply", source = "supplyId")
    EquipmentSupplyUsage toEntity(AttachSupplyForm attachSupplyForm);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EquipmentSupplyUsage partialUpdate(
            EquipmentSupplyUsageDto equipmentSupplyUsageDto, @MappingTarget EquipmentSupplyUsage equipmentSupplyUsage
    );
}