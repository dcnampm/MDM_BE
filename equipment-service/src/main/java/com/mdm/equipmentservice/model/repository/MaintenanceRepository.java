package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.MaintenanceTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRepository extends ParentRepository<MaintenanceTicket, Long> {
    Page<MaintenanceTicket> findByEquipment_Id(Long equipmentId, Pageable pageable);
}