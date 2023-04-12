package com.brad.novel.common.response;

import com.brad.novel.common.error.ResponseCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
public class DataResponse<T> extends Response {
    @JsonProperty(value = "data")
    private T data;

    public DataResponse(ResponseCode responseCode, T data) {
        super(responseCode);
        this.data = data;
    }
    public static <T> DataResponse<T> success(T data){
        return new DataResponse<>(ResponseCode.SUCCESS_201, data);
    }
    public static <T> DataResponse<T> fail(T data){
        return new DataResponse<>(ResponseCode.ERROR_400, data);
    }
}
