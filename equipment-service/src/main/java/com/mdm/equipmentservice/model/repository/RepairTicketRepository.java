package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.RepairTicket;

import java.util.List;

public interface RepairTicketRepository extends ParentRepository<RepairTicket, Long> {
    List<RepairTicket> findByEquipment_IdOrderByIdDesc(Long id);
}