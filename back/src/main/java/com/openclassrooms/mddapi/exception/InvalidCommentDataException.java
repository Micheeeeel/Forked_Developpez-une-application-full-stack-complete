package com.openclassrooms.mddapi.exception;

public class InvalidCommentDataException extends RuntimeException {
    public InvalidCommentDataException(String message) {
        super(message);
    }
}
