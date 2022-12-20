package com.udacity.jwdnd.course1.cloudstorage.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class NoHandlerFoundExceptionAdvice {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException() {
        return "redirect:/home";
    }
}
