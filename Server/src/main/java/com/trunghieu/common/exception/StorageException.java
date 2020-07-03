package com.trunghieu.common.exception;

/**
 * Created on 13/6/2020.
 * Class: StorageException.java
 * By : Admin
 */
public class StorageException extends RuntimeException {
    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
