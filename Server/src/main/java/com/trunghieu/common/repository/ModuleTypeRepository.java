package com.trunghieu.common.repository;

import com.trunghieu.common.model.ModuleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 3/6/2020.
 * Class: ModuleTypeRepository.java
 * By : Admin
 */
@Repository
public interface ModuleTypeRepository extends JpaRepository<ModuleType,Long> {
}
