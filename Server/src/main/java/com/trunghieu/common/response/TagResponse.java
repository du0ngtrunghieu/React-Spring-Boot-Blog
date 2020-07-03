package com.trunghieu.common.response;

import lombok.Data;

/**
 * Created on 25/5/2020.
 * Class: TagResponse.java
 * By : Admin
 */
@Data
public class TagResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private boolean enabled;
}
