package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.SupplyUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyUnitRepository extends ParentRepository<SupplyUnit, Long> {
    public Page<SupplyUnit> findAllByNameContainingIgnoreCase(String keyword, Pageable pageable);
}