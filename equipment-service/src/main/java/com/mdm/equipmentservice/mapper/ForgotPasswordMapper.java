package com.mdm.equipmentservice.mapper;


import com.mdm.equipmentservice.model.entity.ForgotPassword;
import com.mdm.equipmentservice.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ForgotPasswordMapper {
    @Mapping(target = "user", source = "forgotPasswordUser")
    @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "createdAt", expression = "java(System.currentTimeMillis())")
    ForgotPassword toEntity(User forgotPasswordUser);
}
