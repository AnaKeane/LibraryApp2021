package com.LibraryCT.test;

import com.LibraryCT.base.LibraryAppBaseTest;
import com.LibraryCT.pages.LibrarianBookPage;
import com.LibraryCT.pages.LibrarianDashBoardPage;
import com.LibraryCT.pages.LoginPage;
import com.LibraryCT.utilities.BrowserUtils;
import com.LibraryCT.utilities.DB_Utility;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class API_AddNewBook extends LibraryAppBaseTest {
    LibrarianBookPage bookPage=new LibrarianBookPage();
    LoginPage loginPage=new LoginPage();
    LibrarianDashBoardPage dashBoardPage=new LibrarianDashBoardPage();

    @DisplayName("Add One book ,Validate from DB")
    @Test
    public void testAddBookPersisted(){

        Map<String,Object> randomBookMapBody = getRandomBook() ;
        System.out.println("randomBookMapBody = " + randomBookMapBody);

        int newBookId =  given()
                .header("X-LIBRARY-TOKEN" , librarianToken)
                .contentType(ContentType.URLENC)
                .formParams(randomBookMapBody).
                        when()
                .post("/add_book").
                        then()
                // normally it return 201 , this one decided to return 200
                .statusCode(200)
                .log().body()
                .extract()
                .jsonPath().getInt("book_id") ;
        System.out.println("newBookId = " + newBookId);

        DB_Utility.runQuery("SELECT * FROM books where id = " + newBookId ) ;

        Map<String,String> dbResultMap =  DB_Utility.getRowMap(1) ;
        System.out.println("dbResultMap = " + dbResultMap);


        assertThat(dbResultMap.get("name") , is(randomBookMapBody.get("name"))   ) ;


        loginPage.loginToLibraryApp_Librarian();
        BrowserUtils.sleep(3);
        dashBoardPage.bookFunctionality.click();
        BrowserUtils.sleep(5);
        bookPage.searchBox.sendKeys(dbResultMap.get("name")+ Keys.ENTER);
        BrowserUtils.sleep(5);
        String UI_BookName=bookPage.bookName.getText();
        System.out.println("UI_BookName = " + UI_BookName);
        assertThat(randomBookMapBody.get("name"),is(UI_BookName));


    }






}
