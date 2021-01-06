package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(css="#inputFirstName")
    private WebElement firstNameField;

    @FindBy(css="#inputLastName")
    private WebElement lastNameField;

    @FindBy(css="#inputUsername")
    private WebElement usernameField;

    @FindBy(css="#inputPassword")
    private WebElement passwordField;

    @FindBy(css="#submit-button")
    private WebElement submitButton;

    @FindBy(css="#error-msg")
    private WebElement error;


    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signup(String firstname, String lastname, String username, String password){
        this.firstNameField.sendKeys(firstname);
        this.lastNameField.sendKeys(lastname);
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.submitButton.submit();
    }

}
