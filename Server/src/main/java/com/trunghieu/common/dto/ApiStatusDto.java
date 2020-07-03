package com.trunghieu.common.dto;
import lombok.*;
/**
 * Created on 9/4/2020.
 * Class: ApiStatusDto.java
 * By : Admin
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiStatusDto {

    private Boolean success;
    private String message;
    private Object content;
}

