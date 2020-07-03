package com.trunghieu.common.repository;

import com.trunghieu.common.model.Category;
import com.trunghieu.common.model.User;
import com.trunghieu.common.response.PermissionResponse;
import com.trunghieu.common.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

/**
 * Created on 9/4/2020.
 * Class: UserRepository.java
 * By : Admin
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    Page<User> findAllByOrderByIdDesc(Pageable page);
    Boolean findVerifiedByEmail(String email);
    List<User> findByRoleId(Long roleId);

    List<User> findAllByNameContainingOrUsernameContainingOrEmailContaining(String name,String username,String email);

    @Query("select new com.trunghieu.common.response.UserResponse(u.id,u.name,u.username,u.email,u.avatar,u.description,u.status,u.verified,u.createdAt,u.updatedAt) " +
            "from User u " +
            "where (u.name like %:search% or u.username like %:search% or u.email like %:search%) and (u.id not in :ids)" +"")
    List<UserResponse> findUserNotIn(String search, List<Long> ids);

}
