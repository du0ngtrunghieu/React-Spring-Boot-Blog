package com.trunghieu.common.response;

import lombok.Data;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

/**
 * Created on 11/4/2020.
 * Class: PostResponse.java
 * By : Admin
 */
@Data
public class PostResponse {
    private String title;
    private String slug;
    private String summary;
    private Boolean published;
    private String content;
    private String thumbnail;
    private List<CategoryResponse> categories;
    private UserInfoResponse created_by;
    private UserInfoResponse updated_by;
}
