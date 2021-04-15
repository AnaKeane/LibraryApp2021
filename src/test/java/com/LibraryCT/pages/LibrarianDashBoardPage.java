package com.LibraryCT.pages;

import com.LibraryCT.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LibrarianDashBoardPage {
    public LibrarianDashBoardPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }
    @FindBy (xpath = "//a[@href='#books']")
    public WebElement bookFunctionality;
}
