package com.trunghieu.common.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Created on 15/6/2020.
 * Class: VerifyEmailRequestDto.java
 * By : Admin
 */
public class VerifyEmailRequestDto {
    @NotBlank
    @Email
    private String email;

    private Integer otpNo;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOtpNo() {
        return otpNo;
    }

    public void setOtpNo(Integer otpNo) {
        this.otpNo = otpNo;
    }
}
