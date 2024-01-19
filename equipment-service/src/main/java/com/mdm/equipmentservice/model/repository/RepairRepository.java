package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.RepairTicket;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairRepository extends ParentRepository<RepairTicket, Long> {
}