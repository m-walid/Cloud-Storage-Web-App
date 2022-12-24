package com.udacity.jwdnd.course1.cloudstorage.elements;

import org.openqa.selenium.WebElement;

public class CredentialElement {

    private WebElement url;
    private WebElement username;
    private WebElement password;
    private WebElement editButton;
    private WebElement deleteButton;

    public CredentialElement(WebElement url, WebElement username, WebElement password, WebElement editButton, WebElement deleteButton) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.editButton = editButton;
        this.deleteButton = deleteButton;
    }


    public WebElement getUrl() {
        return url;
    }

    public void setUrl(WebElement url) {
        this.url = url;
    }

    public WebElement getUsername() {
        return username;
    }

    public void setUsername(WebElement username) {
        this.username = username;
    }

    public WebElement getPassword() {
        return password;
    }

    public void setPassword(WebElement password) {
        this.password = password;
    }

    public WebElement getEditButton() {
        return editButton;
    }

    public void setEditButton(WebElement editButton) {
        this.editButton = editButton;
    }

    public WebElement getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(WebElement deleteButton) {
        this.deleteButton = deleteButton;
    }
}
