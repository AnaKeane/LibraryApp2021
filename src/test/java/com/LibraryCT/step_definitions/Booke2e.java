package com.LibraryCT.step_definitions;

import com.LibraryCT.base.LibraryAppBaseTest;
import com.LibraryCT.pages.LibrarianBookPage;
import com.LibraryCT.pages.LoginPage;
import com.LibraryCT.utilities.DB_Utility;
import com.LibraryCT.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class Booke2e {
    LoginPage loginPage = new LoginPage();
    LibrarianBookPage booksPage = new LibrarianBookPage();
    Map<String, Object> myBookMap;
    String myToken = LibraryAppBaseTest.getToken("librarian69@library", "KNPXrm3S");
    int bookId;
    String name;

    @Given("user creates a book with api endpoint {string}")
    public void user_creates_a_book_with_api_endpoint(String string) {

        myBookMap = LibraryAppBaseTest.getRandomBook();
        myBookMap.put("name", string);

        bookId = given()
                .baseUri("http://library1.cybertekschool.com")
                .basePath("/rest/v1")
                .log().all()
                .header("x-library-token", myToken)
                .formParams(myBookMap)
                .when()
                .post("/add_book")
                .then()
                .log().all()
                .statusCode(200)
                .extract().jsonPath().getInt("book_id");

        name = myBookMap.get("name").toString();

    }

    @When("user login as librarian")
    public void user_login_as_librarian() {
        loginPage.loginToLibraryApp_Librarian();

    }

    @Then("user should verify that the book is created")
    public void user_should_verify_that_the_book_is_created() {
        booksPage.searchBox.sendKeys(name);
        Assert.assertEquals(booksPage.bookName.getText(),name);
    }

    @Then("user should verify that the book is created in database as well")
    public void user_should_verify_that_the_book_is_created_in_database_as_well() {

        DB_Utility.createConnection();
        String query ="select * from books where id=" +bookId;
        DB_Utility.runQuery(query);
        Map<String,String> dbResultMap =  DB_Utility.getRowMap(1) ;
        assertThat(dbResultMap.get("name") , is(myBookMap.get("name"))   ) ;

    }

}
