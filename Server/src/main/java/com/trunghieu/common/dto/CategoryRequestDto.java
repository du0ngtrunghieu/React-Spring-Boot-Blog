package com.trunghieu.common.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created on 10/4/2020.
 * Class: CategoryRequestDto.java
 * By : Admin
 */
@Data
public class CategoryRequestDto {
    @NotNull
    private String name;

    @NotNull
    private String slug;

    @NotNull
    private boolean enabled;

    @NotNull
    private String description;

    private Long parent_id;
}
