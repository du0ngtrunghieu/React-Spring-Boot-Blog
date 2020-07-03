package com.trunghieu.common.dto;

import com.trunghieu.common.util.constant.APIConstants;
import lombok.Data;

/**
 * Created on 24/5/2020.
 * Class: PaginationDto.java
 * By : Admin
 */
@Data
public class PaginationDto {
    private Integer page;
    private Integer size;

    public PaginationDto(Integer page, Integer size) {
        if(page == null)
            page = 1;
        if(size == null)
            size = 10;
        if(size > APIConstants.MAX_PAGE_SIZE){
            size = APIConstants.MAX_PAGE_SIZE;
        }
        if(page < 0){
            page = 0;
        }
        this.page = page;
        this.size = size;
    }
}
