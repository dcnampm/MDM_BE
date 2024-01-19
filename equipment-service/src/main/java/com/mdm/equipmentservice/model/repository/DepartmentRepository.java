package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.Department;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends ParentRepository<Department, Long> {
    @Override
    @EntityGraph(value = "departmentWithUser")
    Page<Department> findAll(Predicate predicate, Pageable pageable);

    List<Department> findByPhoneIgnoreCase(String phone);

    List<Department> findByEmailIgnoreCase(String email);
}