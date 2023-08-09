package com.openclassrooms.mddapi.exception;

public class InvalidArticleDataException extends RuntimeException {
    public InvalidArticleDataException(String message) {
        super(message);
    }
}
