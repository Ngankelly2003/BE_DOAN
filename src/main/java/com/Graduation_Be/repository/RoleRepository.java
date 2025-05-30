
package com.Graduation_Be.repository;

import com.Graduation_Be.model.RoleEntity;
import com.Graduation_Be.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRoleCode(String roleCode);
}
