package com.udacity.jwdnd.course1.cloudstorage.exceptions;

public class CredentialNotFoundException extends RuntimeException {
    public CredentialNotFoundException(String message) {
        super(message);
    }

    public CredentialNotFoundException() {
        super("Credential not found");
    }
}

