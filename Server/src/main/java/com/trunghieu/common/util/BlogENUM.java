package com.trunghieu.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created on 9/4/2020.
 * Class: BlogENUM.java
 * By : Admin
 */
public class BlogENUM {
    public interface CodeEnum {

        String getCode();

        String getDisplay();
    }

    @Getter
    @AllArgsConstructor
    public enum RoleName implements CodeEnum {
        ROLE_ADMIN("ROLE_ADMIN", "ROLE_ADMIN"),
        ROLE_SUPERADMIN("ROLE_SUPERADMIN", "ROLE_SUPERADMIN"),
        ROLE_USER("ROLE_USER", "ROLE_USER");
        private final String code;
        private final String display;
    }
    @Getter
    @AllArgsConstructor
    public enum STATUS implements CodeEnum {
        ACTIVE("ACTIVE", "ACTIVE"),
        INACTIVE("INACTIVE", "INACTIVE");
        private final String code;
        private final String display;
    }
    @Getter
    @AllArgsConstructor
    public enum PermissionName implements CodeEnum {
        UPDATE("UPDATE","UPDATE"),
        DELETE("DELETE","DELETE"),
        CREATE("CREATE","CREATE"),
        VIEW("VIEW","VIEW");
        private final String code;
        private final String display;
    }
    @Getter
    @AllArgsConstructor
    public enum ModuleName implements CodeEnum {
        POST("POST","POST"),
        TAG("TAG","TAG"),
        CATEGORY("CATEGORY","CATEGORY"),
        COMMENT("COMMENT","COMMENT"),
        USER("USER","USER"),
        PERMISSION("PERMISSION","PERMISSION"),
        ROLE("ROLE","ROLE");
        private final String code;
        private final String display;
    }

    public enum AuditEvent {
        INSERT, UPDATE, DELETE
    }
}
