package com.trunghieu.common.repository;

import com.trunghieu.common.model.Permission;
import com.trunghieu.common.response.PermissionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created on 2/6/2020.
 * Class: PermissionRepository.java
 * By : Admin
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {
    @Query("select new com.trunghieu.common.response.PermissionResponse(p.id,p.name,p.description,p.permissionTypeId,p.moduleId,p.isActive,p.createdAt,p.updatedAt)\n" +
            "from Role r\n" +
            "    join RoleHasPermission rhp on r.id = rhp.roleId\n" +
            "    join Permission p on rhp.permissionId = p.id\n" +
            "where r.id=?1")
    List<PermissionResponse> findPermissionByUserId(Long id);

    List<Permission> findAllByNameContainingOrDescriptionContaining(String name, String description);

    @Query("select new com.trunghieu.common.response.PermissionResponse(p.id,p.name,p.description,p.permissionTypeId,p.moduleId,p.isActive,p.createdAt,p.updatedAt) " +
            "from Permission p " +
            "where (p.name LIKE %:name% or p.description like %:description%) and (p.id not in :ids)")
    List<PermissionResponse> findPermissionNotIN(String name, String description,List<Long> ids);
}
