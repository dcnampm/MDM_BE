package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.HandoverTicket;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HandoverTicketRepository extends ParentRepository<HandoverTicket, Long> {
    List<HandoverTicket> findByEquipment_IdOrderByIdDesc(Long id);

}