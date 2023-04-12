package com.brad.novel.common.response;

import com.brad.novel.common.error.ResponseCode;
import lombok.Getter;

@Getter
public class Response {
    private String code;

    private String message;

    protected Response(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public static Response success(ResponseCode responseCode) {
        return new Response(ResponseCode.SUCCESS_201);
    }

    public static Response fail(ResponseCode responseCode) {
        return new Response(ResponseCode.ERROR_400);
    }
}
