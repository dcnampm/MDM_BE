package com.mdm.equipmentservice.mapper;

//import com.mdm.equipmentservice.keycloak.repository.KeycloakScopePermissionRepository;
//import com.mdm.equipmentservice.keycloak.service.KeycloakPermissionService;
import com.mdm.equipmentservice.model.dto.base.KeycloakRoleDto;
import com.mdm.equipmentservice.model.dto.base.RoleDto;
import com.mdm.equipmentservice.model.dto.form.KeycloakUpsertRoleForm;
import com.mdm.equipmentservice.model.dto.form.UpsertRoleForm;
import com.mdm.equipmentservice.model.dto.fullinfo.RoleFullInfoDto;
import com.mdm.equipmentservice.model.entity.Permission;
import com.mdm.equipmentservice.model.entity.Role;
import com.mdm.equipmentservice.model.repository.PermissionRepository;
import com.mdm.equipmentservice.util.KeycloakUtil;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.authorization.ScopeRepresentation;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {IdToEntityMapper.class})
public abstract class RoleMapper {

//    @Autowired
//    protected KeycloakPermissionService keycloakPermissionService;

//    @Autowired
//    protected KeycloakScopePermissionRepository keycloakScopePermissionRepository;

    @Autowired
    protected PermissionRepository permissionRepository;

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "containerId", ignore = true)
//    @Mapping(target = "composites", ignore = true)
//    @Mapping(target = "composite", ignore = true)
//    @Mapping(target = "clientRole", ignore = true)
//    @Mapping(target = "attributes", ignore = true)
//    public abstract RoleRepresentation toRoleRepresentation(KeycloakUpsertRoleForm keycloakUpsertRoleForm);

//    @InheritConfiguration(name = "toRoleRepresentation")
//    @Mapping(target = "name", source = "keycloakUpsertRoleForm.name")
//    @Mapping(target = "description", source = "keycloakUpsertRoleForm.description")
//    public abstract RoleRepresentation partialUpdate(
//            KeycloakUpsertRoleForm keycloakUpsertRoleForm,
//            @MappingTarget
//            RoleRepresentation roleRepresentationToBeUpdated
//    );

//    @Mapping(target = "scopes", source = "name", qualifiedByName = "toPermissionList")
//    public abstract KeycloakRoleDto toDto(RoleRepresentation roleRepresentation);

//    @Named("toPermissionList")
//    List<String> toPermissionList(String roleName) {
//        String permissionName = KeycloakUtil.getPermissionNameFromRoleName(roleName);
//        if (!keycloakScopePermissionRepository.existByName(permissionName)) {
//            return new ArrayList<>();
//        }
//        return keycloakScopePermissionRepository.findResourceByName(permissionName)
//                .scopes()
//                .stream()
//                .map(ScopeRepresentation::getName)
//                .collect(Collectors.toList());
//
//    }

    public abstract RoleDto toDto(Role role);

    public abstract RoleFullInfoDto toFullInfoDto(Role role);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "permissions", source = "permissions", qualifiedByName = "permissionStringsToPermissionEntities")
    public abstract Role toEntity(UpsertRoleForm upsertRoleForm);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "permissions", source = "permissions", qualifiedByName = "permissionStringsToPermissionEntities")
    public abstract Role partialUpdate(UpsertRoleForm upsertRoleForm, @MappingTarget Role role);

    @Named("permissionStringsToPermissionEntities")
    Set<Permission> permissionStringsToPermissionEntities(Set<String> permissionNames) {
        if (permissionNames == null || permissionNames.isEmpty()) {
            return null;
        }
        return permissionRepository.findByNameInIgnoreCase(permissionNames);
    }
}
