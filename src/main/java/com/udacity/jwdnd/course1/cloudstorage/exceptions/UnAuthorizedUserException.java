package com.udacity.jwdnd.course1.cloudstorage.exceptions;

public class UnAuthorizedUserException extends RuntimeException {
    public UnAuthorizedUserException(String message) {
        super(message);
    }

    public UnAuthorizedUserException() {
        super("User is not authorized to perform this action");
    }
}

