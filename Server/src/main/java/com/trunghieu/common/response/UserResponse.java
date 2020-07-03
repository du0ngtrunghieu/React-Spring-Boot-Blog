package com.trunghieu.common.response;

import com.trunghieu.common.model.Role;
import com.trunghieu.common.util.BlogENUM;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

/**
 * Created on 12/6/2020.
 * Class: UserResponse.java
 * By : Admin
 */
@Data
public class UserResponse {
    public Long id;
    public String name;
    public String username;
    public String email;
    public String avatar;
    private String description;
    private BlogENUM.STATUS status;
    public RoleDetailResponse role;
    public FileResponse file;
    public boolean verified;
    private Instant createdAt;
    private Instant updatedAt;

    public UserResponse(Long id, String name, String username, String email, String avatar, String description, BlogENUM.STATUS status, boolean verified, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.description = description;
        this.status = status;
        this.verified = verified;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UserResponse() {
    }
}
