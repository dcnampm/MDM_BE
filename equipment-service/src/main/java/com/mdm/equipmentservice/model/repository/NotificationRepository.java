package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.NotificationsFromMgdb;
import com.querydsl.core.types.Predicate;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends ParentRepository<NotificationsFromMgdb, Long>{
    @NotNull Page<NotificationsFromMgdb> findAll(@NotNull Predicate predicate, @NotNull Pageable pageable);

    void deleteById(Long id);

}
