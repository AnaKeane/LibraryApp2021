package com.LibraryCT.pages;

import com.LibraryCT.utilities.ConfigurationReader;
import com.LibraryCT.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public LoginPage() {

        PageFactory.initElements(Driver.getDriver(), this);

    }

    @FindBy (id = "inputEmail")
    public WebElement usernameBox;

    @FindBy(id = "inputPassword")
    public WebElement passwordBox;

    @FindBy(xpath = "//button[@class='btn btn-lg btn-primary btn-block']")
    public WebElement loginButton;
    @FindBy(xpath = "//a[@class='navbar-brand']")
    public WebElement librarySign;

    public void loginToLibraryApp_Librarian(){
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
        usernameBox.sendKeys(ConfigurationReader.getProperty("librarianUsername"));
        passwordBox.sendKeys(ConfigurationReader.getProperty("librarianPassword"));
        loginButton.click();

    }



}
