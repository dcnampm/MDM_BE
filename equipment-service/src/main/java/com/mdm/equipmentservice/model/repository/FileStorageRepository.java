package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.FileStorage;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStorageRepository extends ParentRepository<FileStorage, Long> {
}