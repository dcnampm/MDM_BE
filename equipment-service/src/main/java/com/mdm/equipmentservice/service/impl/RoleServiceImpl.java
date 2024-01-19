package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.mapper.RoleMapper;
import com.mdm.equipmentservice.model.dto.form.UpsertRoleForm;
import com.mdm.equipmentservice.model.dto.fullinfo.RoleFullInfoDto;
import com.mdm.equipmentservice.model.entity.Permission;
import com.mdm.equipmentservice.model.entity.Role;
import com.mdm.equipmentservice.model.repository.PermissionRepository;
import com.mdm.equipmentservice.model.repository.RoleRepository;
import com.mdm.equipmentservice.query.param.GetRolesQueryParam;
import com.mdm.equipmentservice.query.predicate.RolePredicate;
import com.mdm.equipmentservice.service.RoleService;
import com.mdm.equipmentservice.util.MessageUtil;
import com.mdm.equipmentservice.util.UniqueValidationUtil;
import com.mdm.equipmentservice.util.ValidationUtil;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final MessageUtil messageUtil;

    private final RoleMapper roleMapper;

    private final ValidationUtil validationUtil;

    private final UniqueValidationUtil uniqueValidationUtil;

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    public RoleServiceImpl(
            MessageUtil messageUtil, RoleMapper roleMapper, ValidationUtil validationUtil, UniqueValidationUtil uniqueValidationUtil,
            RoleRepository roleRepository,
            PermissionRepository permissionRepository) {
        this.messageUtil = messageUtil;
        this.roleMapper = roleMapper;
        this.validationUtil = validationUtil;
        this.uniqueValidationUtil = uniqueValidationUtil;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Page<RoleFullInfoDto> getRoles(GetRolesQueryParam getRolesQueryParam, Pageable pageable) {
        Predicate rolePredicate = RolePredicate.getRolePredicate(getRolesQueryParam);
        return roleRepository.findAll(rolePredicate, pageable).map(roleMapper::toFullInfoDto);
    }

    @Override
    public RoleFullInfoDto createRole(UpsertRoleForm upsertRoleForm) {
        Role role = roleMapper.toEntity(upsertRoleForm);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                role,
                roleRepository,
                messageUtil.getMessage("roleUniquenessValidationFailed")
        );
        role = roleRepository.save(role);
        return roleMapper.toFullInfoDto(role);
    }

    @Override
    public RoleFullInfoDto updateRole(Long roleId, UpsertRoleForm upsertRoleForm) {
        validationUtil.validateNotFound(roleId, roleRepository, messageUtil.getMessage("roleNotFound"));
        Role role = roleRepository.findById(roleId).orElseThrow();
        role = roleMapper.partialUpdate(upsertRoleForm, role);
        uniqueValidationUtil.validateUnique_throwExceptionIfHasError(
                role,
                roleRepository,
                messageUtil.getMessage("roleUniquenessValidationFailed")
        );
        role = roleRepository.save(role);
        return roleMapper.toFullInfoDto(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        validationUtil.validateNotFound(roleId, roleRepository, messageUtil.getMessage("roleNotFound"));
        roleRepository.deleteById(roleId);
    }

    @Override
    public RoleFullInfoDto getRoleById(Long roleId) {
        validationUtil.validateNotFound(roleId, roleRepository, messageUtil.getMessage("roleNotFound"));
        Role role = roleRepository.findById(roleId).orElse(null);
        return roleMapper.toFullInfoDto(role);
    }

    @Override
    public void createPermissions() {
        Set<String> permissionNames = Set.of(
                "DASHBOARD.READ",
                "EQUIPMENT.READ",
                "EQUIPMENT.CREATE",
                "EQUIPMENT.UPDATE",
                "EQUIPMENT.DELETE",
                "HANDOVER.READ",
                "HANDOVER.CREATE",
                "HANDOVER.ACCEPT",
                "MAINTENANCE.READ",
                "MAINTENANCE.CREATE",
                "MAINTENANCE.ACCEPT",
                "MAINTENANCE.UPDATE",
                "INSPECTION.READ",
                "INSPECTION.CREATE",
                "INSPECTION.ACCEPT",
                "INSPECTION.UPDATE",
                "TRANSFER.READ",
                "TRANSFER.CREATE",
                "TRANSFER.ACCEPT",
                "REPORT_BROKEN.READ",
                "REPORT_BROKEN.CREATE",
                "REPORT_BROKEN.ACCEPT",
                "REPAIR.READ",
                "REPAIR.CREATE",
                "REPAIR.ACCEPT",
                "REPAIR.UPDATE",
                "LIQUIDATION.READ",
                "LIQUIDATION.CREATE",
                "LIQUIDATION.ACCEPT",
                "SUPPLY.READ",
                "SUPPLY.CREATE",
                "SUPPLY.UPDATE",
                "SUPPLY.DELETE",
                "EQUIPMENT_CATEGORY.READ",
                "EQUIPMENT_CATEGORY.CREATE",
                "EQUIPMENT_CATEGORY.UPDATE",
                "EQUIPMENT_CATEGORY.DELETE",
                "SUPPLY_UNIT.READ",
                "SUPPLY_UNIT.CREATE",
                "SUPPLY_UNIT.UPDATE",
                "SUPPLY_UNIT.DELETE",
                "EQUIPMENT_GROUP.READ",
                "EQUIPMENT_GROUP.CREATE",
                "EQUIPMENT_GROUP.UPDATE",
                "EQUIPMENT_GROUP.DELETE",
                "DEPARTMENT.READ",
                "DEPARTMENT.CREATE",
                "DEPARTMENT.UPDATE",
                "DEPARTMENT.DELETE",
                "USER.READ",
                "USER.CREATE",
                "USER.UPDATE",
                "USER.DELETE",
                "ROLE.READ",
                "ROLE.CREATE",
                "ROLE.UPDATE",
                "ROLE.DELETE",
                "EQUIPMENT_UNIT.READ",
                "EQUIPMENT_UNIT.CREATE",
                "EQUIPMENT_UNIT.UPDATE",
                "EQUIPMENT_UNIT.DELETE",
                "PROJECT.READ",
                "PROJECT.CREATE",
                "PROJECT.UPDATE",
                "PROJECT.DELETE",
                "SUPPLIER.READ",
                "SUPPLIER.CREATE",
                "SUPPLIER.UPDATE",
                "SUPPLIER.DELETE",
                "SERVICE.READ",
                "SERVICE.UPDATE",
                "SERVICE.DELETE",
                "SERVICE.CREATE"
        );
        for (String permissionName : permissionNames) {
            Permission permission = new Permission();
            permission.setName(permissionName);
            try {
                permissionRepository.save(permission);
            } catch (Exception e) {
                System.out.println("Permission " + permissionName + " already exists");
            }
        }
    }
}
