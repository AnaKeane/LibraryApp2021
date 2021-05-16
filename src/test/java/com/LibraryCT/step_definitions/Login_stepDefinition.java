package com.LibraryCT.step_definitions;

import com.LibraryCT.pages.LoginPage;
import com.LibraryCT.utilities.BrowserUtils;
import com.LibraryCT.utilities.ConfigurationReader;
import com.LibraryCT.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.junit.Assert;

public class Login_stepDefinition {
    LoginPage loginPage = new LoginPage();
    @Given("the user is on the log in page")
    public void the_user_is_on_the_log_in_page() {
        BrowserUtils.sleep(3);
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));

    }


    @When("the user inputs username")
    public void the_user_inputs_username() {
        loginPage.usernameBox.sendKeys(ConfigurationReader.getProperty("librarianUsername"));

    }
    @When("the user inputs password")
    public void the_user_inputs_password() {
        loginPage.passwordBox.sendKeys(ConfigurationReader.getProperty("librarianPassword"));
        loginPage.loginButton.click();
        //System.out.println("Driver.getDriver().getTitle() = " + Driver.getDriver().getTitle());


    }
    @Then("the user is logged in to main page")
    public void the_user_is_logged_in_to_main_page() {
        Assert.assertTrue(loginPage.librarySign.isDisplayed());

    }
}
