package com.udacity.jwdnd.course1.cloudstorage.exceptions;

public class FileAlreadyExistsException extends RuntimeException {
    public FileAlreadyExistsException() {
        super("File already exists");
    }
}

