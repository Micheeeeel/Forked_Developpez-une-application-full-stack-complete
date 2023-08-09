package com.openclassrooms.mddapi.exception;

public class InvalidSubjectDataException extends RuntimeException {
    public InvalidSubjectDataException(String message) {
        super(message);
    }
}
