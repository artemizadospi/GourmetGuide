package com.pweb.gourmetguide.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid username!")
public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException(){}
    public InvalidUsernameException(String message) {
        super(message);
    }
}
