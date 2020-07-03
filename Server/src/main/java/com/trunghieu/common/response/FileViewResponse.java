package com.trunghieu.common.response;

import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * Created on 25/6/2020.
 * Class: FileViewResponse.java
 * By : Admin
 */
@Data
public class FileViewResponse {
    public String id;
    public String name;
    public  Boolean isDir;
    public String ext;
    public Boolean isHidden;
    public Boolean isSymlink;
    public Long size;
    public Instant modDate;
    public String parentId; // ID of the parent folder
    public List<String> childrenIds; // An array of IDs of children (only for folders)
    public String thumbnailUrl;
    public String pathReal;
}
