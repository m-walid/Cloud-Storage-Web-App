package com.udacity.jwdnd.course1.cloudstorage.elements;

import com.udacity.jwdnd.course1.cloudstorage.dtos.NoteDto;
import org.openqa.selenium.WebElement;

public class NoteElement {
    private WebElement noteTitle;
    private WebElement noteDescription;
    private WebElement noteEditButton;
    private WebElement noteDeleteButton;

    public NoteElement(WebElement noteTitle, WebElement noteDescription, WebElement noteEditButton, WebElement noteDeleteButton) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteEditButton = noteEditButton;
        this.noteDeleteButton = noteDeleteButton;
    }

    public WebElement getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(WebElement noteTitle) {
        this.noteTitle = noteTitle;
    }

    public WebElement getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(WebElement noteDescription) {
        this.noteDescription = noteDescription;
    }

    public WebElement getNoteEditButton() {
        return noteEditButton;
    }

    public void setNoteEditButton(WebElement noteEditButton) {
        this.noteEditButton = noteEditButton;
    }

    public WebElement getNoteDeleteButton() {
        return noteDeleteButton;
    }

    public void setNoteDeleteButton(WebElement noteDeleteButton) {
        this.noteDeleteButton = noteDeleteButton;
    }
}
