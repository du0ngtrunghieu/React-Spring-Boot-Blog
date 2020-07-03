package com.trunghieu.common.repository;

import com.trunghieu.common.model.RoleHasPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created on 22/4/2020.
 * Class: RoleHasPermissionRepository.java
 * By : Admin
 */
@Repository
public interface RoleHasPermissionRepository extends JpaRepository<RoleHasPermission,Long> {
    Optional<RoleHasPermission> findByPermissionIdAndRoleId(Long permissionId,Long roleId);
     void removeByRoleIdAndPermissionId(Long roleId, Long permissionId);
     List<RoleHasPermission> findAllByRoleId(Long roleId);
     void removeByRoleId(Long roleId);
     void deleteByRoleId(Long roleId);
}
