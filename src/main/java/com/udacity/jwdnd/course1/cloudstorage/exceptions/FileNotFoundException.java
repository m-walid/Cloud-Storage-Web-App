package com.udacity.jwdnd.course1.cloudstorage.exceptions;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException() {
        super("File not found");
    }
}
