package com.openclassrooms.mddapi.exception;

public class UpdateUserException extends RuntimeException {
    public UpdateUserException(String message) {
        super(message);
    }
}