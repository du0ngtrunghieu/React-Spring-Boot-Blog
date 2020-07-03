package com.trunghieu.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Created on 13/6/2020.
 * Class: FileResponse.java
 * By : Admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private String name;
    private String uri;
    private String type;
    private long size;
    private Instant createdAt;
    private Instant updatedAt;

}
