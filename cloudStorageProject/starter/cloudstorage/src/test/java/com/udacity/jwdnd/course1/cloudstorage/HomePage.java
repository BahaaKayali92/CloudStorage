package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {
    @FindBy(id = "logout-button")
    WebElement logout;

    @FindBy(id = "show-note-modal")
    WebElement showNoteModalButton;

    @FindBy(id = "note-title")
    WebElement noteTitle;

    @FindBy(id = "note-description")
    WebElement noteDescription;

    @FindBy(id = "noteSubmit")
    WebElement noteSubmitButton;

    @FindBy(id = "nav-notes-tab")
    WebElement noteTab;

    @FindBy(className = "note-row")
    List<WebElement> notes;

    @FindBy(className = "edit-note")
    List<WebElement> editNoteButton;

    @FindBy(className = "delete-note")
    List<WebElement> deleteNoteButton;

    @FindBy(id = "show-credential-modal")
    WebElement showCredentialModalButton;

    @FindBy(id = "nav-credentials-tab")
    WebElement credentialTab;

    @FindBy(id = "credential-url")
    WebElement credentialUrl;

    @FindBy(id = "credential-username")
    WebElement credentialUsername;

    @FindBy(id = "credential-password")
    WebElement credentialPassword;

    @FindBy(id = "credential-save")
    WebElement credentialSaveButton;

    @FindBy(className = "credential-row")
    List<WebElement> credentials;

    @FindBy(className = "edit-credential")
    List<WebElement> editCredentialButton;

    @FindBy(className = "delete-credential")
    List<WebElement> deleteCredentialButton;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        this.logout.click();
    }

    public void showNoteTab() {
        this.noteTab.click();
    }

    public void openNoteModal() {
        this.showNoteModalButton.click();
    }

    public void addNote(String noteTitle, String noteDescription){
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.sendKeys(noteDescription);
        this.noteSubmitButton.click();
    }

    public int getNotesCount() {
        if (this.notes != null) {
            return this.notes.size();
        }
        return 0;
    }

    public void editNote() {
        if (editNoteButton.size() > 0) {
            editNoteButton.get(0).click();
        }
    }

    public void deleteNote() {
        if (deleteNoteButton.size() > 0) {
            deleteNoteButton.get(0).click();
        }
    }

    public void showCredentialTab() {
        this.credentialTab.click();
    }

    public void openCredentialmodal() {
        this.showCredentialModalButton.click();
    }

    public void addCredential(String credentialUrl, String credentialUsername, String credentialPassword){
        this.credentialUrl.sendKeys(credentialUrl);
        this.credentialUsername.sendKeys(credentialUsername);
        this.credentialPassword.sendKeys(credentialPassword);
        this.credentialSaveButton.click();
    }

    public int getCredentialsCount() {
        if (this.credentials != null) {
            return this.credentials.size();
        }

        return 0;
    }

    public void editCredential() {
        if (editCredentialButton.size() > 0) {
            editCredentialButton.get(0).click();
        }
    }

    public void deleteCredential() {
        if (deleteCredentialButton.size() > 0) {
            deleteCredentialButton.get(0).click();
        }
    }
}
