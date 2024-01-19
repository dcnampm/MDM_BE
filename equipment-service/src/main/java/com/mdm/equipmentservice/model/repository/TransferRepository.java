package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.TransferTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends ParentRepository<TransferTicket, Long> {
    Page<TransferTicket> findByEquipment_Id(Long id, Pageable pageable);
}