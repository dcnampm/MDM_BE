package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.model.dto.base.ServiceDto;
import com.mdm.equipmentservice.model.dto.form.UpsertServiceForm;
import com.mdm.equipmentservice.model.entity.Service;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {IdToEntityMapper.class})
public interface ServiceMapper {

    @Mapping(target = "id", ignore = true)
    Service toEntity(UpsertServiceForm upsertServiceForm);

    ServiceDto toDto(Service service);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Service partialUpdate(
            UpsertServiceForm upsertServiceForm, @MappingTarget Service service
    );
}