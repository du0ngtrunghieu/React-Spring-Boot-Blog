package com.trunghieu.common.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created on 25/5/2020.
 * Class: TagRequestDto.java
 * By : Admin
 */
@Data
public class TagRequestDto {

    @NotNull
    private String name;
    @NotNull
    private String slug;
    @NotNull
    private String description;
    @NotNull
    private boolean enabled;
}
