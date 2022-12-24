package com.udacity.jwdnd.course1.cloudstorage.exceptions;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(String message) {
        super(message);
    }

    public NoteNotFoundException() {
        super("Note not found");
    }
}

