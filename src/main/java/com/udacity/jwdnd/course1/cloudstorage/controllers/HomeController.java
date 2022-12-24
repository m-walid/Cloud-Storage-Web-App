package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.dtos.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.dtos.NoteDto;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {
    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;

    public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping("/home")
    public String getHomeView(@ModelAttribute("noteDto") NoteDto noteDto, @ModelAttribute("credentialDto") CredentialDto credentialDto, Model model) {
        File[] userFiles = this.fileService.getUserFiles();
        Note[] userNotes = this.noteService.getUserNotes();
        CredentialDto[] userCredentials = this.credentialService.getUserCredentials();
        model.addAttribute("files", userFiles);
        model.addAttribute("notes", userNotes);
        model.addAttribute("credentials", userCredentials);
        return "home";
    }
}
