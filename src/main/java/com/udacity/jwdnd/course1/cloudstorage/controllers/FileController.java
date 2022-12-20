package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, RedirectAttributes redirectAttributes) {
        try {
            fileService.saveFile(fileUpload);
        }
        catch (RuntimeException e) {
            System.out.println(e.getCause());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/home";
    }

    @PostMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, RedirectAttributes redirectAttributes) {
        try {
            fileService.deleteFile(fileId);
        }
        catch (RuntimeException e) {
            System.out.println(e.getCause());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/home";
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<byte[]> getFile(@PathVariable("fileId") Integer fileId) {
        try {
            File file = fileService.getFile(fileId);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                    .body(file.getData());

        }
        catch (RuntimeException e) {
            System.out.println(e.getCause());
            return ResponseEntity.notFound().build();
        }
    }
}
