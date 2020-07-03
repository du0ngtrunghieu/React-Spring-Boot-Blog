package com.trunghieu.common.util;

import com.trunghieu.common.exception.BadRequestException;
import com.trunghieu.common.util.constant.APIConstants;

/**
 * Created on 10/4/2020.
 * Class: PagedUtils.java
 * By : Admin
 */
public class PagedUtils {
    public static void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > APIConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + APIConstants.MAX_PAGE_SIZE);
        }
    }
}
