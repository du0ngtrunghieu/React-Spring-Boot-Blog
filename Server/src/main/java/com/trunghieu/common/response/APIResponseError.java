package com.trunghieu.common.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created on 9/4/2020.
 * Class: APIResponseError.java
 * By : Admin
 */
@Getter
@Setter
@Builder

public class APIResponseError {
    public long timestamp;
    public int code;
    @JsonInclude(value = Include.NON_NULL)
    public String error;
    public Object message;
    public List<Object> listMessage;


    public APIResponseError() {
    }

    public APIResponseError(int code, Object message) {
        this.timestamp = new Date().getTime();
        this.code = code;
        this.message = message;
    }

    public APIResponseError(int code,  String error,Object message) {
        this.timestamp = new Date().getTime();
        this.code = code;
        this.message = message;
    }

    public APIResponseError(long timestamp, int code, String error, Object message, List<Object> listMessage) {
        this.timestamp = new Date().getTime();
        this.code = code;
        this.error = error;
        this.message = message;
        this.listMessage = listMessage;
    }

}
