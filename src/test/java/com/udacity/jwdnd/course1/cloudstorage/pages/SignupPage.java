package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.dtos.SignupDto;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(id = "inputFirstName")
    private WebElement firstNameInput;

    @FindBy(id = "inputLastName")
    private WebElement lastNameInput;

    @FindBy(id = "inputUsername")
    private WebElement usernameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    @FindBy(id = "buttonSignUp")
    private WebElement signupButton;

    private final WebDriver webDriver;

    public SignupPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(this.webDriver, this);
    }

    public void signup(SignupDto signupDto) {
        this.firstNameInput.sendKeys(signupDto.getFirstname());
        this.lastNameInput.sendKeys(signupDto.getLastname());
        this.usernameInput.sendKeys(signupDto.getUsername());
        this.passwordInput.sendKeys(signupDto.getPassword());
        this.signupButton.click();
    }

    public boolean checkIfOnLoginPage(){
        return this.webDriver.getCurrentUrl().contains("login");
    }

    public void clearInputs() {
        this.firstNameInput.clear();
        this.lastNameInput.clear();
        this.usernameInput.clear();
        this.passwordInput.clear();
    }

}
