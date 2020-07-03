package com.trunghieu.common.response;

import lombok.Data;

/**
 * Created on 3/6/2020.
 * Class: RoleResponse.java
 * By : Admin
 */
@Data
public class RoleResponse {
    public Long id;
    public String name;
    public String displayName;
    public Long countPermissions;
    public Long countUsers;

    public RoleResponse(Long id, String name, String displayName, Long countPermissions, Long countUsers) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.countPermissions = countPermissions;
        this.countUsers = countUsers;
    }
}
