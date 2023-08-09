package com.openclassrooms.mddapi.exception;

public class DeleteArticleException extends RuntimeException {
    public DeleteArticleException(String message) {
        super(message);
    }
}