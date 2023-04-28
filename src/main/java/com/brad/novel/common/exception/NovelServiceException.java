package com.brad.novel.common.exception;

import com.brad.novel.common.error.ResponseCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NovelServiceException extends RuntimeException {
    private final ResponseCode responseCode;
}
