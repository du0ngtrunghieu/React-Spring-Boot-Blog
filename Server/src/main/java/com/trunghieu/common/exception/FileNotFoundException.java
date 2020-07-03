package com.trunghieu.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created on 13/6/2020.
 * Class: FileNotFoundException.java
 * By : Admin
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundException extends StorageException{
    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
