package com.mdm.equipmentservice.model.repository;

import com.mdm.equipmentservice.model.dto.base.UserDto;
import com.mdm.equipmentservice.model.entity.User;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends ParentRepository<User, Long> {
    List<User> findByRoleNameInIgnoreCase(Collection<String> roleNames);
    Optional<User> findByEmailIgnoreCase(String email);
    Page<UserDto> findByUsernameInIgnoreCase(Collection<String> usernames, Pageable pageable);

    @EntityGraph(value = "userWithRole")
    Optional<User> findByUsernameIgnoreCase(String username);
    @EntityGraph(value = "userWithRole")
    Optional<User> findByUsername(String username);

    @Override
//    @EntityGraph(value = "userFullInfo")
    Page<User> findAll(Predicate predicate, Pageable pageable);

    List<User> findAllByRole_IdIn(List<Long> roleIds);
}