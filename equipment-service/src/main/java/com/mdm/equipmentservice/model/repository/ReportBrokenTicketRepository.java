package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.ReportBrokenTicket;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportBrokenTicketRepository extends ParentRepository<ReportBrokenTicket, Long> {
    List<ReportBrokenTicket> findByEquipment_IdOrderByIdDesc(Long id);
}