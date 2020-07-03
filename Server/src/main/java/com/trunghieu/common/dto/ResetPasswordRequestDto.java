package com.trunghieu.common.dto;

import lombok.Data;

/**
 * Created on 15/6/2020.
 * Class: ResetPasswordRequestDto.java
 * By : Admin
 */
@Data
public class ResetPasswordRequestDto {
    public String email;
    public String password;
    private Integer otpNo;
}
