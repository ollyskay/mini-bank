package org.example.bankprojectibk.Repository;

import org.example.bankprojectibk.Model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String aLong);
}
