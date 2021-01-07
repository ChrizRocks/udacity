package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id="nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(name = "noteSubmit")
    private WebElement noteSubmitBtn;

    @FindBy(id = "note-title")
    private WebElement noteTitleText;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionText;

    @FindBy(className = "btn btn-info float-right")
    private WebElement addNoteBtn;

    public void navNotes(WebDriver driver) throws InterruptedException {
        this.notesTab.click();
    }

    public void navCredentials(WebDriver driver) throws InterruptedException {
        this.credentialsTab.click();
    }
    public void createNote(WebDriver driver, String title, String description) throws InterruptedException {

        Thread.sleep(5000);

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", notesTab);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(addNoteBtn)).click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteTitleText)).sendKeys(title);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteDescriptionText)).sendKeys(description);
        noteSubmitBtn.click();
    }
}
