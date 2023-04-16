package com.brad.novel.point.exception;

public class PointDuplicatedException extends RuntimeException {
    public PointDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PointDuplicatedException(String message) {
        super(message);
    }
}
