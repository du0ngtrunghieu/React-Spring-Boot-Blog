package com.trunghieu.api.audit;

import com.trunghieu.common.dto.AuditTrailDto;
import com.trunghieu.common.model.AuditTrail;
import com.trunghieu.common.repository.AuditTrailRepository;
import org.springframework.stereotype.Service;

/**
 * Created on 2/6/2020.
 * Class: AuditLogService.java
 * By : Admin
 */
@Service
public class AuditLogService {
    private final AuditTrailRepository auditTrailRepository;

    public AuditLogService(AuditTrailRepository auditTrailRepository) {
        this.auditTrailRepository = auditTrailRepository;
    }

    public void save(AuditTrailDto auditTrailDTO){
        auditTrailRepository.save(new AuditTrail(auditTrailDTO));
    }
}
