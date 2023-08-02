package com.openclassrooms.mddapi.exception;

public class SubjectAlreadyExistsException extends RuntimeException {
    public SubjectAlreadyExistsException(String message) {
        super(message);
    }
}