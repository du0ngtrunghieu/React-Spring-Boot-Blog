package com.trunghieu.common.response;

import com.trunghieu.common.model.Role;
import com.trunghieu.common.util.BlogENUM;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * Created on 11/4/2020.
 * Class: UserInfoResponse.java
 * By : Admin
 */
@Data
public class UserInfoResponse {
    public String name;
    public String username;
    public String email;
    public String avatar;
    private String description;
    private BlogENUM.STATUS status;
    public List<Role> roles;
}
