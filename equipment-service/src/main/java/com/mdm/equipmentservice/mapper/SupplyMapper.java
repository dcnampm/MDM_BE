package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.model.dto.base.SupplyDto;
import com.mdm.equipmentservice.model.dto.form.UpsertSupplyForm;
import com.mdm.equipmentservice.model.dto.fullinfo.SupplyFullInfoDto;
import com.mdm.equipmentservice.model.entity.Supply;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {IdToEntityMapper.class})
public abstract class SupplyMapper {



    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "projectId", target = "project")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "unitId", target = "unit")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract Supply toSupplyEntity(UpsertSupplyForm upsertSupplyForm);

    @Mapping(source = "upsertSupplyForm.supplierId", target = "supplier")
    @Mapping(source = "upsertSupplyForm.projectId", target = "project")
    @Mapping(source = "upsertSupplyForm.categoryId", target = "category")
    @Mapping(source = "upsertSupplyForm.unitId", target = "unit")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract Supply partialUpdate(UpsertSupplyForm upsertSupplyForm, @MappingTarget Supply supply);

    public abstract SupplyFullInfoDto toFullInfoDto(Supply supply);
}
