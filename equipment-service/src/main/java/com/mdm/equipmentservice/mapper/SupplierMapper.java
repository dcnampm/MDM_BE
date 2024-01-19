package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.model.dto.form.UpsertSupplierForm;
import com.mdm.equipmentservice.model.dto.fullinfo.SupplierFullInfoDto;
import com.mdm.equipmentservice.model.entity.Service;
import com.mdm.equipmentservice.model.entity.Supplier;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {IdToEntityMapper.class})
public abstract class SupplierMapper {

    @Mapping(target = "supplies", ignore = true)
    @Mapping(target = "services", source = "serviceIds")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "equipments", ignore = true)
    @Mapping(target = "contactPerson", source = "contactPersonId")
    public abstract Supplier toEntity(UpsertSupplierForm upsertSupplierForm);

    @Mapping(target = "supplies", ignore = true)
    @Mapping(target = "services", source = "serviceIds")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "equipments", ignore = true)
    @Mapping(target = "contactPerson", source = "contactPersonId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Supplier partialUpdate(
            UpsertSupplierForm upsertSupplierForm, @MappingTarget Supplier supplier
    );

    public abstract SupplierFullInfoDto toDto(Supplier supplier);

    List<String> servicesToServiceNames(List<Service> services) {
        return services.stream().map(Service::getName).collect(Collectors.toList());
    }
}