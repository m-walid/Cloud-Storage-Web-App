package com.udacity.jwdnd.course1.cloudstorage.exceptionHandlers;

import org.apache.tomcat.util.http.fileupload.impl.SizeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler({SizeException.class, MaxUploadSizeExceededException.class})
    public String handleFileSizeLimitExceededException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "File size is too large");
        return "redirect:/home";
    }
}
