package com.pweb.gourmetguide.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Access to resource denied")
public class CommentConflictException extends RuntimeException {
    public CommentConflictException() {}
    public CommentConflictException(String message) {
        super(message);
    }
}