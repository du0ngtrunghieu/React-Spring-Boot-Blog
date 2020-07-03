package com.trunghieu.common.dto;

import com.trunghieu.common.util.BlogENUM;
import lombok.Data;

/**
 * Created on 19/4/2020.
 * Class: PermissionRequestDto.java
 * By : Admin
 */
@Data
public class PermissionRequestDto {
    private String name;
    private String description;
    private Long moduleTypeId;
    private Long permissionTypeId;
    private boolean isActive;
}
