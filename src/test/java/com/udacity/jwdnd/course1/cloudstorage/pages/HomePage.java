package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.elements.CredentialElement;
import com.udacity.jwdnd.course1.cloudstorage.elements.NoteElement;
import org.openqa.selenium.*;
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

    @FindBy(id = "add-note-btn")
    private WebElement addNoteButton;

    @FindBy(id = "notes-body")
    private WebElement notesBody;

    @FindBy(id = "note-save-changes")
    private WebElement noteSubmit;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id = "credentials-body")
    private WebElement credentialsBody;
    @FindBy(id = "credential-save-changes")
    private WebElement credentialSubmit;
    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credential-close-btn")
    private WebElement credentialCloseButton;

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


    public List<NoteElement> getNotes() {
        return this.notesBody.findElements(By.tagName("tr")).stream().map(note -> new NoteElement(
                note.findElement(By.className("note-title")),
                note.findElement(By.className("note-description")),
                note.findElement(By.className("note-edit-btn")),
                note.findElement(By.className("note-delete-btn"))
        )).collect(Collectors.toUnmodifiableList());
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

    public void goToCredentialsTab() {
        this.credentialsTab.click();
        this.webDriverWait.until(webDriver -> this.credentialsBody.isDisplayed());
    }

    public void createCredential(String url, String username, String password) {
        this.goToCredentialsTab();
        this.addCredentialButton.click();
        this.webDriverWait.until(webDriver -> this.credentialUrl.isDisplayed());
        this.credentialUrl.sendKeys(url);
        this.credentialUsername.sendKeys(username);
        this.credentialPassword.sendKeys(password);
        this.credentialSubmit.click();
    }

    public void editCredential(String url, String username, String password) {
        this.goToCredentialsTab();
        this.getCredentials().get(0).getEditButton().click();
        this.webDriverWait.until(webDriver -> this.credentialUrl.isDisplayed());
        this.credentialUrl.clear();
        this.credentialUsername.clear();
        this.credentialPassword.clear();
        this.credentialUrl.sendKeys(url);
        this.credentialUsername.sendKeys(username);
        this.credentialPassword.sendKeys(password);
        this.credentialSubmit.click();
    }

    public void deleteCredential() {
        this.goToCredentialsTab();
        this.getCredentials().get(0).getDeleteButton().click();
    }

    public List<CredentialElement> getCredentials() {
        return this.credentialsBody.findElements(By.tagName("tr")).stream().map(credential -> new CredentialElement(
                credential.findElement(By.className("credential-url")),
                credential.findElement(By.className("credential-username")),
                credential.findElement(By.className("credential-password")),
                credential.findElement(By.className("credential-edit-btn")),
                credential.findElement(By.className("credential-delete-btn"))
        )).collect(Collectors.toUnmodifiableList());
    }

    public boolean checkIfCredentialExists(String url, String username) {
        this.webDriverWait.until(webDriver -> this.credentialsTab.isDisplayed());
        this.goToCredentialsTab();
        return this.getCredentials().stream()
                .anyMatch(credential -> credential.getUrl().getText().equals(url) && credential.getUsername().getText().equals(username));
    }

    public boolean checkIfPasswordIsEncrypted(String url, String username, String password) {
        this.webDriverWait.until(webDriver -> this.credentialsTab.isDisplayed());
        this.goToCredentialsTab();
        return this.getCredentials().stream()
                .filter(credential -> credential.getUrl().getText().equals(url) && credential.getUsername().getText().equals(username))
                .findFirst()
                .filter(credential -> credential.getPassword().getText().equals(password))
                .isEmpty();
    }

    public boolean checkIfPasswordIsDecrypted(String url, String username, String password) {
        this.webDriverWait.until(webDriver -> this.credentialsTab.isDisplayed());
        this.goToCredentialsTab();
        CredentialElement credentialElement = this.getCredentials().stream()
                .filter(credential -> credential.getUrl().getText().equals(url) && credential.getUsername().getText().equals(username))
                .findFirst().orElseThrow(() -> new NoSuchElementException("No credential found"));
        credentialElement.getEditButton().click();
        this.webDriverWait.until(webDriver -> this.credentialPassword.isDisplayed());
        boolean isDecrypted = this.credentialPassword.getAttribute("value").equals(password);
        this.credentialCloseButton.click();
        return isDecrypted;
    }

    public void deleteAllCredentials() {
        this.goToCredentialsTab();
        for (CredentialElement credential : this.getCredentials()) {
            this.deleteCredential();
            this.goToCredentialsTab();
        }
    }

    public boolean checkIfOnLoginPage() {
        return this.webDriver.getCurrentUrl().contains("login");
    }
}
