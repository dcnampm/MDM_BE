package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.Service;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends ParentRepository<Service, Long> {
}