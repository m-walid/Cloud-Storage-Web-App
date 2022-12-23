package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dtos.NoteDto;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private final NoteMapper noteMapper;
    private final AuthenticationService authenticationService;

    public NoteService(NoteMapper noteMapper, AuthenticationService authenticationService) {
        this.noteMapper = noteMapper;
        this.authenticationService = authenticationService;
    }

    public Integer addNote(NoteDto noteDto) {
        User currentUser = authenticationService.getCurrentUser();
        Note note = new Note(
                noteDto.getTitle(),
                noteDto.getDescription(),
                currentUser.getId()
        );
        return this.noteMapper.add(note);
    }

    public Note[] getUserNotes() {
        User currentUser = authenticationService.getCurrentUser();
        return this.noteMapper.findByUserId(currentUser.getId());
    }

    public void deleteNote(Integer noteId) {
        Note note = this.noteMapper.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));
        User currentUser = authenticationService.getCurrentUser();
        if (!note.getUserId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to delete this note");
        }
        this.noteMapper.delete(noteId);
    }

    public void updateNote(NoteDto noteDto) {
        Note note = this.noteMapper.findById(noteDto.getId()).orElseThrow(() -> new RuntimeException("Note not found"));
        User currentUser = authenticationService.getCurrentUser();
        if (!note.getUserId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to update this note");
        }
        note.setTitle(noteDto.getTitle());
        note.setDescription(noteDto.getDescription());
        this.noteMapper.update(note);
    }

    public void addOrUpdate(NoteDto noteDto) {
        if (noteDto.getId() == null) {
            this.addNote(noteDto);
        } else {
            this.updateNote(noteDto);
        }
    }
}
