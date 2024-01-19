package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.Inventory;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends ParentRepository<Inventory, Long> {
}