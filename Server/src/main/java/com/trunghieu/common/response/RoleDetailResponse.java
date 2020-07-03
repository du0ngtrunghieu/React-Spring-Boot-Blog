package com.trunghieu.common.response;

import com.trunghieu.common.model.Permission;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created on 3/6/2020.
 * Class: RoleDetailResponse.java
 * By : Admin
 */
@Data
@NoArgsConstructor
public class RoleDetailResponse {
    private Long id;
    private String name;
    private String displayName;
    private List<PermissionResponse> permissions;
    private List<UserResponse> userResponses;
    private boolean isStaff;
    private boolean isAdministrator;

    public RoleDetailResponse(Long id, String name, String displayName, List<PermissionResponse> permissions, boolean isStaff, boolean isAdministrator) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.permissions = permissions;
        this.isStaff = isStaff;
        this.isAdministrator = isAdministrator;
    }
}
