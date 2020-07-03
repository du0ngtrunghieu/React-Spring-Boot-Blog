package com.trunghieu.common.repository;

import com.trunghieu.common.model.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 3/6/2020.
 * Class: PermissionTypeRepository.java
 * By : Admin
 */
@Repository
public interface PermissionTypeRepository extends JpaRepository<PermissionType,Long> {
}
