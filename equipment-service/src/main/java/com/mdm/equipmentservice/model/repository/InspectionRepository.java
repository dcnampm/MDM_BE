package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.InspectionTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectionRepository extends ParentRepository<InspectionTicket, Long> {
    Page<InspectionTicket> findByEquipment_Id(Long equipmentId, Pageable pageable);
}