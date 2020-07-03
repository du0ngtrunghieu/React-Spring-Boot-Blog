package com.trunghieu.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2/6/2020.
 * Class: AuditTrailDto.java
 * By : Admin
 */
@Data
@NoArgsConstructor
public class AuditTrailDto {
    private String className;
    private String persistedObjectId;
    private String eventName;
    private String propertyName;
    private String oldValue;
    private String newValue;

    public AuditTrailDto(String className, String persistedObjectId, String eventName, String propertyName, String oldValue, String newValue) {
        this.className = className;
        this.persistedObjectId = persistedObjectId;
        this.eventName = eventName;
        this.propertyName = propertyName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
}
