package com.trunghieu.common.response;

import lombok.Data;

import java.time.Instant;

/**
 * Created on 4/6/2020.
 * Class: PermissionResponse.java
 * By : Admin
 */
@Data
public class PermissionResponse {
    private Long id;
    private String name;
    private String description;
    private Long permissionTypeId;
    private Long moduleId;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;

    public PermissionResponse(Long id, String name, String description, Long permissionTypeId, Long moduleId, boolean isActive, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.permissionTypeId = permissionTypeId;
        this.moduleId = moduleId;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PermissionResponse() {
    }
}
