package com.trunghieu.common.model;

import com.trunghieu.common.dto.AuditTrailDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created on 2/6/2020.
 * Class: AuditTrail.java
 * By : Admin
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "logs")
public class AuditTrail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateCreated;
    private LocalDate lastUpdated;
    private String className;
    private String persistedObjectId;
    private String eventName;
    private String propertyName;
    private String oldValue;
    private String newValue;

    @PrePersist
    void beforeInsert() {
        this.dateCreated = LocalDate.now();
        this.lastUpdated = LocalDate.now();
    }

    @PreUpdate
    void beforeUpdate() {
        this.lastUpdated = LocalDate.now();
    }

    public AuditTrail(AuditTrailDto auditTrailDTO) {
        this.className = auditTrailDTO.getClassName();
        this.persistedObjectId = auditTrailDTO.getPersistedObjectId();
        this.eventName = auditTrailDTO.getEventName();
        this.propertyName = auditTrailDTO.getPropertyName();
        this.oldValue = auditTrailDTO.getOldValue();
        this.newValue = auditTrailDTO.getNewValue();
    }
}
