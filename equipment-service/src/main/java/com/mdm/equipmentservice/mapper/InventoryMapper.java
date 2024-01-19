package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.model.dto.base.InventoryDto;
import com.mdm.equipmentservice.model.entity.Inventory;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryMapper {
    @Mapping(source = "inventoryPersonUsername", target = "inventoryPerson.username")
    @Mapping(source = "inventoryPersonName", target = "inventoryPerson.name")
    Inventory toEntity(InventoryDto inventoryDto);

    @InheritInverseConfiguration(name = "partialUpdate")
    InventoryDto toDto(Inventory inventory);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Inventory partialUpdate(
            InventoryDto inventoryDto, @MappingTarget Inventory inventory
    );
}