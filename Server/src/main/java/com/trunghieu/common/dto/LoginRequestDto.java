package com.trunghieu.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created on 9/4/2020.
 * Class: LoginRequestDto.java
 * By : Admin
 */
@Data
public class LoginRequestDto {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;
}
