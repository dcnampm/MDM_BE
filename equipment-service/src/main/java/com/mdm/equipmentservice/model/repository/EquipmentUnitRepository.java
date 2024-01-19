package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.EquipmentUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentUnitRepository extends ParentRepository<EquipmentUnit, Long> {
    Page<EquipmentUnit> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}