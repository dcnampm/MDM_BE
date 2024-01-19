package com.mdm.equipmentservice.mapper;

import com.mdm.equipmentservice.exception.ResourceNotFoundException;
import com.mdm.equipmentservice.model.entity.*;
import com.mdm.equipmentservice.model.repository.*;
import com.mdm.equipmentservice.util.MessageUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * A mapper for mapping id to entity, used for mapping relationship, and avoid circular dependency
 * <br>
 * By using this mapper, we can avoid creating the empty relationship entity if the given id is null
 * <br>
 * For example, if we want to create a new equipment, and the equipment has a relationship with equipment category, we can use this mapper to map the equipment
 * category id to equipment category entity, if the equipment category id is null, then the equipment category entity will be null, and the equipment category
 * entity will not be created
 * <br>
 * This can also help we avoid N+1 query problem by using it with {@link jakarta.persistence.NamedEntityGraph} and
 * {@link org.springframework.data.jpa.repository.EntityGraph}
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class IdToEntityMapper {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected EquipmentUnitRepository equipmentUnitRepository;

    @Autowired
    protected EquipmentGroupRepository equipmentGroupRepository;

    @Autowired
    protected ServiceRepository serviceRepository;

    @Autowired
    protected DepartmentRepository departmentRepository;

    @Autowired
    protected EquipmentRepository equipmentRepository;

    @Autowired
    protected EquipmentCategoryRepository equipmentCategoryRepository;

    @Autowired
    protected SupplyRepository supplyRepository;

    @Autowired
    protected ProjectRepository projectRepository;

    @Autowired
    protected SupplierRepository supplierRepository;

    @Autowired
    protected SupplyUnitRepository supplyUnitRepository;

    @Autowired
    protected SupplyCategoryRepository supplyCategoryRepository;

    @Autowired
    protected EquipmentSupplyUsageRepository equipmentSupplyUsageRepository;

    @Autowired
    protected MessageUtil messageUtil;

    @Autowired
    protected PermissionRepository permissionRepository;

    @Autowired
    protected RoleRepository roleRepository;

    public User toUser(Long userId) {
        return ObjectUtils.isNotEmpty(userId) ?
                this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("resourceNotFound"))) : null;
    }

    public Department toDepartment(Long departmentId) {
        return ObjectUtils.isNotEmpty(departmentId) ?
                this.departmentRepository.findById(departmentId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("resourceNotFound"))) :
                null;
    }

    public List<Department> toListDepartment(List<Long> departmentIds) {
        if (departmentIds == null || departmentIds.isEmpty()) {
            return null;
        }
        List<Long> blankFilteredDepartmentIds = departmentIds.stream().filter(ObjectUtils::isNotEmpty).toList();
        return departmentRepository.findAllById(blankFilteredDepartmentIds);
    }

    EquipmentCategory toCategory(Long categoryId) {
        return ObjectUtils.isNotEmpty(categoryId) ?
                equipmentCategoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("resourceNotFound"))) :
                null;
    }

    List<Supply> toSupplies(List<Long> supplyIds) {
        if (supplyIds.isEmpty())
            return null;
        return supplyRepository.findAllById(supplyIds);
    }

    Supplier toSupplier(Long supplierId) {
        return ObjectUtils.isNotEmpty(supplierId) ?
                supplierRepository.findById(supplierId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("resourceNotFound"))) : null;
    }

    List<EquipmentSupplyUsage> toEquipmentSupplyUsages(List<Long> equipmentSupplyUsageIds) {
        if (equipmentSupplyUsageIds == null || equipmentSupplyUsageIds.isEmpty())
            return null;
        return equipmentSupplyUsageRepository.findAllById(equipmentSupplyUsageIds);
    }

    Project toProject(Long projectId) {
        return ObjectUtils.isNotEmpty(projectId) ?
                projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("resourceNotFound"))) : null;
    }

    List<Supplier> toSuppliers(List<Long> supplierIds) {
        if (supplierIds.isEmpty())
            return null;
        return supplierRepository.findAllById(supplierIds);
    }

    List<Service> serviceIdsToServices(List<Long> serviceIds) {
        if (serviceIds == null || serviceIds.isEmpty())
            return null;
        return ObjectUtils.isNotEmpty(serviceIds) ? serviceRepository.findAllById(serviceIds) : null;
    }

    SupplyUnit toSupplyUnit(Long supplyUnitId) {
        return ObjectUtils.isNotEmpty(supplyUnitId) ? supplyUnitRepository.findById(supplyUnitId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("resourceNotFound"))) : null;
    }

    SupplyCategory toSupplyCategory(Long supplyCategoryId) {
        return ObjectUtils.isNotEmpty(supplyCategoryId) ? supplyCategoryRepository.findById(supplyCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("resourceNotFound"))) : null;
    }

    List<Equipment> toEquipments(List<Long> equipmentIds) {
        if (equipmentIds == null || equipmentIds.isEmpty()) {
            return null;
        }
        return equipmentRepository.findAllById(equipmentIds);
    }

    Equipment toEquipment(Long equipmentId) {
        return ObjectUtils.isNotEmpty(equipmentId) ?
                equipmentRepository.findById(equipmentId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("resourceNotFound"))) : null;
    }

    Supply toSupply(Long supplyId) {
        return ObjectUtils.isNotEmpty(supplyId) ?
                supplyRepository.findById(supplyId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("resourceNotFound"))) : null;
    }

    EquipmentGroup toEquipmentGroup(Long equipmentGroupId) {
        return ObjectUtils.isNotEmpty(equipmentGroupId) ? equipmentGroupRepository.findById(equipmentGroupId)
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("resourceNotFound"))) : null;
    }

    EquipmentUnit toEquipmentUnit(Long unitId) {
        return ObjectUtils.isNotEmpty(unitId) ?
                equipmentUnitRepository.findById(unitId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("resourceNotFound"))) : null;
    }

    Set<Permission> toPermissions(Set<Long> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty()) {
            return null;
        }
        return permissionRepository.findByIdIn(permissionIds);
    }

    Role toRole(Long roleId) {
        return ObjectUtils.isNotEmpty(roleId) ?
                roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("resourceNotFound"))) : null;
    }
}
