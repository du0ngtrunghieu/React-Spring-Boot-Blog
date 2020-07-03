package com.trunghieu.common.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created on 9/4/2020.
 * Class: SignUpRequestDto.java
 * By : Admin
 */
@Data
public class SignUpRequestDto {
    @NotBlank
    @Size(min = 4, max = 40)
    private String name;

    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
}
