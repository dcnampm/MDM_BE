package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.EquipmentGroup;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentGroupRepository extends ParentRepository<EquipmentGroup, Long> {
}