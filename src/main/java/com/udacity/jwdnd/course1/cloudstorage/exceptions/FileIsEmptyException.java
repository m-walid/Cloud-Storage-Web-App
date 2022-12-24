package com.udacity.jwdnd.course1.cloudstorage.exceptions;

public class FileIsEmptyException extends RuntimeException {
    public FileIsEmptyException() {
        super("File is empty");
    }
}


