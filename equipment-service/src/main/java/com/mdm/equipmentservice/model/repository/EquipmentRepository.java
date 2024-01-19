package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.Equipment;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentRepository extends ParentRepository<Equipment, Long> {

    @Override
    @EntityGraph(value = "equipmentFullInfoDto")
    Optional<Equipment> findById(Long id);
}