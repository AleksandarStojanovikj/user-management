package com.emt.lab.usermanagement.repository;

import com.emt.lab.usermanagement.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(String roleName);
}
