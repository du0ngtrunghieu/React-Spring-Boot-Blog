package com.trunghieu.common.repository;

import com.trunghieu.common.model.AuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 3/6/2020.
 * Class: AuditTrailRepository.java
 * By : Admin
 */
public interface AuditTrailRepository extends JpaRepository<AuditTrail,Long> {
}
