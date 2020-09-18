package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(id = "inputFirstName")
    WebElement firstname;

    @FindBy(id = "inputLastName")
    WebElement lastname;

    @FindBy(id = "inputUsername")
    WebElement username;

    @FindBy(id = "inputPassword")
    WebElement password;

    @FindBy(id = "submit-button")
    WebElement submit;

    @FindBy(id = "success-msg")
    WebElement signupSuccessMessage;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signup(String firstname, String lastname, String username, String password) {
        this.firstname.sendKeys(firstname);
        this.lastname.sendKeys(lastname);
        this.username.sendKeys(username);
        this.password.sendKeys(password);

        this.submit.click();
    }

    public String getSuccessMessage() {
        return this.signupSuccessMessage.getText();
    }
}
