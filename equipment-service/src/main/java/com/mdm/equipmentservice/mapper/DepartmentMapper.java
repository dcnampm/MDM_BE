package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.model.dto.base.DepartmentDto;
import com.mdm.equipmentservice.model.dto.form.UpsertDepartmentForm;
import com.mdm.equipmentservice.model.dto.fullinfo.DepartmentFullInfoDto;
import com.mdm.equipmentservice.model.entity.Department;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {IdToEntityMapper.class, UserMapper.class})
public abstract class DepartmentMapper {

    public abstract DepartmentDto departmentToDepartmentDto(Department department);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "activeStatus", expression = "java(DepartmentActiveStatus.ACTIVE)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "headOfDepartment", source = "headOfDepartmentId")
    @Mapping(target = "chiefNurse", source = "chiefNurseId")
    @Mapping(target = "contactPerson", source = "contactPersonId")
    @Mapping(target = "manager", source = "managerId")
    public abstract Department toEntity(UpsertDepartmentForm upsertDepartmentForm);

    @InheritConfiguration(name = "toEntity")
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Department partialUpdate(UpsertDepartmentForm upsertDepartmentForm, @MappingTarget Department department);

    public abstract DepartmentFullInfoDto toFullInfoDto(Department department);

}
