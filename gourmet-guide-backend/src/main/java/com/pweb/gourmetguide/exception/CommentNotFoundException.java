package com.pweb.gourmetguide.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Comment does not exist")
public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException() {}
    public CommentNotFoundException(String message) {
        super(message);
    }
}