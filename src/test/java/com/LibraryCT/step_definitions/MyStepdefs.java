package com.LibraryCT.step_definitions;

import com.LibraryCT.base.LibraryAppBaseTest;
import com.LibraryCT.pages.LibrarianBookPage;
import com.LibraryCT.pages.LibrarianDashBoardPage;
import com.LibraryCT.pages.LoginPage;
import com.LibraryCT.utilities.BrowserUtils;
import com.LibraryCT.utilities.DB_Utility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import org.openqa.selenium.Keys;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MyStepdefs extends LibraryAppBaseTest {
    LibrarianBookPage bookPage=new LibrarianBookPage();
    LoginPage loginPage=new LoginPage();
    LibrarianDashBoardPage dashBoardPage=new LibrarianDashBoardPage();
    String myToken=LibraryAppBaseTest.getToken("librarian69@library", "KNPXrm3S");
    Map<String,Object> randomBookMapBody;
    Map<String,String> dbResultMap;
    int newBookId;
    @Given("user add a new book with API")
    public void userAddANewBookWithAPI() {



        randomBookMapBody = getRandomBook() ;
        System.out.println("randomBookMapBody = " + randomBookMapBody);

        newBookId =  given()
                .header("x-library-token", myToken)
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
    }

    @When("user validate new book is in DataBase")
    public void userValidateNewBookIsInDataBase() {
        DB_Utility.createConnection();
        BrowserUtils.sleep(3);
        DB_Utility.runQuery("select * from books where id = " + newBookId ) ;

        dbResultMap =  DB_Utility.getRowMap(1) ;
        System.out.println("dbResultMap = " + dbResultMap);

        assertThat(dbResultMap.get("name") , is(randomBookMapBody.get("name"))   ) ;


    }

    @Then("user could search book by name on LibraryCT app")
    public void userCouldSearchBookByNameOnLibraryCTApp() {

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
