package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.Supplier;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends ParentRepository<Supplier, Long> {

}