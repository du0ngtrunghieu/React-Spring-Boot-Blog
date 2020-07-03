package com.trunghieu.common.repository;

import com.trunghieu.common.model.Role;
import com.trunghieu.common.response.RoleDetailResponse;
import com.trunghieu.common.response.RoleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 9/4/2020.
 * Class: RoleRepository.java
 * By : Admin
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    //    Optional<Role> findByName(BlogENUM.RoleName roleName);
//    @Query("select " +
//            "new com.trunghieu.common.response.UserRoleResponse(r.id,r.name,count(distinct u.id),count(distinct mp.id),r.createdAt)" +
//            "from Role r " +
//            "left join User u on r.id = u.roleId\n" +
//            "left join RoleHasPermission rhp on r.id = rhp.roleId\n" +
//            "left join Permission p on rhp.permissionId = p.id\n" +
//            "left join ModulePermission mp on p.id = mp.permissionId\n" +
//            "group by r.id\n" +
//            "order by count(distinct mp.id) DESC"
//
//    )
//    List<UserRoleResponse> findAllRoleCountPermissionUser();
    @Query("select new com.trunghieu.common.response.RoleResponse(r.id, r.name,r.displayName,count(distinct p.id),count(distinct u.id) )" +
            "from Role r left join RoleHasPermission rhp on r.id = rhp.roleId\n" +
            "left join Permission p on rhp.permissionId = p.id\n" +
            "left join User u on u.roleId = r.id\n" +
            "group by r.id\n" +
            "order by count(distinct p.id) desc")
    List<RoleResponse> findAllRoleCountPermissionUser();

//    @Query("select new com.trunghieu.common.response.RoleDetailResponse(r.id, r.name,r.displayName,p.id,p.name) from Role r join RoleHasPermission rhp on r.id = rhp.roleId\n" +
//            "join Permission p on rhp.permissionId = p.id\n" +
//            "where r.id=?1")
//    RoleDetailResponse findRoleById(Long id);

}
