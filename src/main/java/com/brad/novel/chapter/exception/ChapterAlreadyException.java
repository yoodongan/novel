package com.brad.novel.chapter.exception;

public class ChapterAlreadyException extends RuntimeException {
    public ChapterAlreadyException(String message) {
        super(message);
    }
}
