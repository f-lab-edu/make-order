package com.makeorder.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponse<T> extends BaseResponseBody {

    private T data;

    public static <T> DataResponse of(Integer statusCode, String message, T data) {
        DataResponse dataResponse = new DataResponse();
        dataResponse.setStatusCode(statusCode);
        dataResponse.setMessage(message);
        dataResponse.setData(data);
        return dataResponse;
    }
}
