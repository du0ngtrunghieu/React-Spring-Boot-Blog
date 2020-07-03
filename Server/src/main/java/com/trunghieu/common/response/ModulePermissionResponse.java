package com.trunghieu.common.response;

import com.trunghieu.common.util.BlogENUM;
import lombok.Data;

import java.time.Instant;

/**
 * Created on 22/4/2020.
 * Class: ModulePermissionResponse.java
 * By : Admin
 */
@Data
public class ModulePermissionResponse {
    private Long id;
    private String displayName;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
}
