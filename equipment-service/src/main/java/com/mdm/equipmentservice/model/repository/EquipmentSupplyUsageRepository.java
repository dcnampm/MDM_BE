package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.EquipmentSupplyUsage;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EquipmentSupplyUsageRepository extends ParentRepository<EquipmentSupplyUsage, Long> {
    List<EquipmentSupplyUsage> findByEquipment_IdIn(Collection<Long> ids);

    List<EquipmentSupplyUsage> findBySupply_IdIn(Collection<Long> ids);

}