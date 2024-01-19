package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.model.dto.base.SupplyUnitDto;
import com.mdm.equipmentservice.model.entity.SupplyUnit;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class SupplyUnitMapper {
    public abstract SupplyUnitDto toDto(SupplyUnit supplyUnit);

    public abstract SupplyUnit toEntity(SupplyUnitDto supplyUnitDto);

    @Mapping(target = "id", source = "supplyUnitId")
    public abstract SupplyUnit toEntity_Update(SupplyUnitDto supplyUnitDto, Long supplyUnitId);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract SupplyUnit partialUpdate(
            SupplyUnitDto supplyUnitDto,@MappingTarget SupplyUnit supplyUnit
    );
}
