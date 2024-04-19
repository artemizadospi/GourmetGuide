package com.pweb.gourmetguide.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Post does not exist")
public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {}
    public PostNotFoundException(String message) {
        super(message);
    }
}