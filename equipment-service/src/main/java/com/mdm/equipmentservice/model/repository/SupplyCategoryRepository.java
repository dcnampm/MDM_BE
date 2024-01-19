package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.SupplyCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyCategoryRepository extends ParentRepository<SupplyCategory, Long> {
}