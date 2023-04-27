package com.brad.novel.common.response;

import com.brad.novel.common.error.ResponseCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataResponse<T> {
    @JsonProperty(value = "data")
    private T data;

    public DataResponse(ResponseCode responseCode, T data) {
        super();
        this.data = data;
    }
    public static <T> DataResponse<T> success(ResponseCode responseCode, T data){
        return new DataResponse<>(responseCode, data);
    }
    public static <T> DataResponse<T> fail(ResponseCode responseCode, T data){
        return new DataResponse<>(responseCode, data);
    }
}
