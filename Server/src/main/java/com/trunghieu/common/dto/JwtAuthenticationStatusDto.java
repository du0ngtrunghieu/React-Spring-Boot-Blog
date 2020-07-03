package com.trunghieu.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 9/4/2020.
 * Class: JwtAuthenticationStatusDto.java
 * By : Admin
 */
@Data
public class JwtAuthenticationStatusDto {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthenticationStatusDto(String accessToken) {
        this.accessToken = accessToken;
    }

}
