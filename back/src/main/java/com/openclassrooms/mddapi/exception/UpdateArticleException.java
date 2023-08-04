package com.openclassrooms.mddapi.exception;

public class UpdateArticleException extends RuntimeException {
    public UpdateArticleException(String message) {
        super(message);
    }
}