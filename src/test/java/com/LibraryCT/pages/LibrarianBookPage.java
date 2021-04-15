package com.LibraryCT.pages;

import com.LibraryCT.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LibrarianBookPage {
    public LibrarianBookPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }
    @FindBy(xpath = "//input[@type='search']")
    public WebElement searchBox;
    @FindBy(xpath = "//tbody//td[3]")
    public WebElement bookName;


}
