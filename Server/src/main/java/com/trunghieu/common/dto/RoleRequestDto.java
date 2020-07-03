package com.trunghieu.common.dto;

import lombok.Data;

/**
 * Created on 4/6/2020.
 * Class: RoleRequestDto.java
 * By : Admin
 */
@Data
public class RoleRequestDto {
    private String name;
    public String displayName;
    public Boolean isStaff;
}
