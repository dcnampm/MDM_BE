package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.EquipmentCategory;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentCategoryRepository extends ParentRepository<EquipmentCategory, Long> {
    @Override
    @EntityGraph(value = "fullInfo")
    Page<EquipmentCategory> findAll(Predicate predicate, Pageable pageable);

    List<EquipmentCategory> findAllByGroupId(Long groupId);
}