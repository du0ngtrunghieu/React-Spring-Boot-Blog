package com.trunghieu.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Created on 9/4/2020.
 * Class: AppException.java
 * By : Admin
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
