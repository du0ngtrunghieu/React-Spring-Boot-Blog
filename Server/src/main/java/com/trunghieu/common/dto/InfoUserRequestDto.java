package com.trunghieu.common.dto;

import com.trunghieu.common.util.BlogENUM;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created on 13/6/2020.
 * Class: InfoUserRequestDto.java
 * By : Admin
 */
@Data
public class InfoUserRequestDto {
    public String name;
    public String description;
    public BlogENUM.STATUS status;
    public Long roleId;
    public MultipartFile avatar;
}
