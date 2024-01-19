package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.LiquidationTicket;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquidationTicketRepository extends ParentRepository<LiquidationTicket, Long> {
}