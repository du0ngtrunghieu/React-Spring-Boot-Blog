package com.trunghieu.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created on 9/4/2020.
 * Class: AuthenticationException.java
 * By : Admin
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends RuntimeException{
    public AuthenticationException(String message) {
        super(message);
    }
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}

