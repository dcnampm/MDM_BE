package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.model.dto.base.SupplyCategoryDto;
import com.mdm.equipmentservice.model.dto.form.UpsertSupplyCategoryForm;
import com.mdm.equipmentservice.model.entity.SupplyCategory;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplyCategoryMapper {

    SupplyCategoryDto toDto(SupplyCategory supplyCategory);

    SupplyCategory toEntity(UpsertSupplyCategoryForm upsertSupplyCategoryForm);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SupplyCategory partialUpdate(UpsertSupplyCategoryForm upsertSupplyCategoryForm, @MappingTarget SupplyCategory supplyCategory);
}