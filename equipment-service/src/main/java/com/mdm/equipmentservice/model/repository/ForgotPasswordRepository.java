package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.entity.ForgotPassword;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends ParentRepository<ForgotPassword, Long> {
    Optional<ForgotPassword> findByUuid(String uuid);
}