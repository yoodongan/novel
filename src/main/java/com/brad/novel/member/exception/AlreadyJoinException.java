package com.brad.novel.member.exception;

public class AlreadyJoinException extends RuntimeException {
    public AlreadyJoinException(String message) {
        super(message);
    }
}
