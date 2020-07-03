package com.trunghieu.common.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created on 13/6/2020.
 * Class: StorageProperties.java
 * By : Admin
 */
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
