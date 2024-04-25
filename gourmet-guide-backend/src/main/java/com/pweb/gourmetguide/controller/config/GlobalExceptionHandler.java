package com.pweb.gourmetguide.controller.config;

import com.pweb.gourmetguide.exception.CommentConflictException;
import com.pweb.gourmetguide.exception.CommentNotFoundException;
import com.pweb.gourmetguide.exception.InvalidCredentialsException;
import com.pweb.gourmetguide.exception.InvalidUsernameException;
import com.pweb.gourmetguide.exception.PostNotFoundException;
import com.pweb.gourmetguide.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException() {
        return new ResponseEntity<>("User does not exist!", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentialsException() {
        return new ResponseEntity<>("Wrong password! Please try again!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PostNotFoundException.class)
    public ResponseEntity<Object> handlePostNotFoundException() {
        return new ResponseEntity<>("Post does not exist!", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CommentNotFoundException.class)
    public ResponseEntity<Object> handleCommentNotFoundException() {
        return new ResponseEntity<>("Comment does not exist!", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CommentConflictException.class)
    public ResponseEntity<Object> handleCommentConflictException() {
        return new ResponseEntity<>("Access to comment denied!", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = InvalidUsernameException.class)
    public ResponseEntity<Object> handleInvalidUsernameException() {
        return new ResponseEntity<>("Wrong username, please try again!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}