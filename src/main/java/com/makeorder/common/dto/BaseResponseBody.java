package com.makeorder.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponseBody {

    private String message = null;

    private Integer statusCode = null;

    public BaseResponseBody(Integer statusCode){
        this.statusCode = statusCode;
    }

    public BaseResponseBody(Integer statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }

    public static BaseResponseBody of(Integer statusCode, String message) {
        return new BaseResponseBody(statusCode, message);
    }
}