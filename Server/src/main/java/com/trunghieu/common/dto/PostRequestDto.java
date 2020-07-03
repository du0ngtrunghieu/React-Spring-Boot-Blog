package com.trunghieu.common.dto;

import lombok.Data;

import javax.persistence.Column;
import java.util.List;

/**
 * Created on 11/4/2020.
 * Class: PostRequest.java
 * By : Admin
 */
@Data
public class PostRequestDto {
    private String title;
    private String slug;
    private String summary;
    private Boolean published;
    private String content;
    private String thumbnail;
    private List<Long> categoryId;
}
