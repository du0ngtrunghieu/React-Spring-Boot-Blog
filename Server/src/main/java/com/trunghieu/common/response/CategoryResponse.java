package com.trunghieu.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trunghieu.common.model.Category;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

/**
 * Created on 10/4/2020.
 * Class: CategoryResponse.java
 * By : Admin
 */
@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private boolean enabled;
    private List<Category> subCategories;
    private String nameParent;
    private Instant createdAt;
    private Instant updatedAt;
}
