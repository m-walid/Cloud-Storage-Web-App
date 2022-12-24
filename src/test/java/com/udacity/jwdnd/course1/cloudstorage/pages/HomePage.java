package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.elements.NoteElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage {

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "add-note-btn")
    private WebElement addNoteButton;

    @FindBy(id="notes-body")
    private WebElement notesBody;
    @FindBy(id = "note-save-changes")
    private WebElement noteSubmit;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmit;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;


    private final WebDriver webDriver;
    private final WebDriverWait webDriverWait;

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(this.webDriver, this);
        this.webDriverWait = new WebDriverWait(this.webDriver, 10);
    }
    public void logout() {
        this.logoutButton.click();
    }

    public void goToNotesTab() {
        this.notesTab.click();
        this.webDriverWait.until(webDriver -> this.notesBody.isDisplayed());
    }

    public void goToCredentialsTab() {
        this.credentialsTab.click();
    }

    public void createNote(String title, String description) {
        this.goToNotesTab();
        this.addNoteButton.click();
        this.webDriverWait.until(webDriver -> this.noteTitle.isDisplayed());
        this.noteTitle.sendKeys(title);
        this.noteDescription.sendKeys(description);
        this.noteSubmit.click();
    }


    public void editNote(String title, String description) {
        this.goToNotesTab();
        this.getNotes().get(0).getNoteEditButton().click();
        this.webDriverWait.until(webDriver -> this.noteTitle.isDisplayed());
        this.noteTitle.clear();
        this.noteDescription.clear();
        this.noteTitle.sendKeys(title);
        this.noteDescription.sendKeys(description);
        this.noteSubmit.click();
    }

    public void deleteNote() {
        this.goToNotesTab();
        this.getNotes().get(0).getNoteDeleteButton().click();
    }


    public List<NoteElement> getNotes(){
        return this.notesBody.findElements(By.tagName("tr")).stream().map(note -> new NoteElement(
                note.findElement(By.className("note-title")),
                note.findElement(By.className("note-description")),
                note.findElement(By.className("note-edit-btn")),
                note.findElement(By.className("note-delete-btn"))
        )).collect(Collectors.toUnmodifiableList());
    }
    public boolean checkIfOnLoginPage() {
        return this.webDriver.getCurrentUrl().contains("login");
    }

    public boolean checkIfNoteExists(String title, String description) {
        this.webDriverWait.until(webDriver -> this.notesTab.isDisplayed());
        this.goToNotesTab();
        return this.getNotes().stream().anyMatch(note -> note.getNoteTitle().getText().equals(title) && note.getNoteDescription().getText().equals(description));
    }

    public void deleteAllNotes() {
        this.goToNotesTab();
        this.getNotes().forEach(note -> {
            note.getNoteDeleteButton().click();
        });
    }
}
