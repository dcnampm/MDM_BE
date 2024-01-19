package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.model.dto.base.UserDto;
import com.mdm.equipmentservice.model.dto.form.UpsertUserForm;
import com.mdm.equipmentservice.model.dto.fullinfo.UserDetailDto;
import com.mdm.equipmentservice.model.entity.User;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {IdToEntityMapper.class, RoleMapper.class})
public abstract class UserMapper {

    @Autowired
    protected BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mapping(target = "role", source = "roleId")
    @Mapping(target = "departmentResponsibilities", source = "departmentResponsibilityIds")
    @Mapping(target = "department", source = "departmentId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    public abstract User toEntity(UpsertUserForm upsertUserForm);

    @InheritConfiguration(name = "toEntity")
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract User partialUpdate(UpsertUserForm upsertUserForm, @MappingTarget User userToBeUpdated);

    abstract UserDto toDto(User user);


    @Mapping(target = "grantedAuthorities", expression = "java(com.mdm.equipmentservice.util.SecurityUtil.getCurrentLoggedInUserAuthorities())")
    public abstract UserDetailDto toUserDetailDto(User user, Long imageId);

    /**
     * this is the method that is called when updating user profile. User cannot change their own username, password, enabled, workingStatus, roleName,
     * department, departmentResponsibilities
     *
     * @param upsertUserForm
     * @param user
     * @return
     */
    @Mapping(target = "role", source = "roleId")
    @Mapping(target = "departmentResponsibilities", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "workingStatus", ignore = true)
    @Mapping(target = "username", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract User updateProfile(UpsertUserForm upsertUserForm, @MappingTarget User user);

    @Named("encodePassword")
    public String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public abstract List<UserDetailDto> toUserDetailDtoList(List<User> users);

}
