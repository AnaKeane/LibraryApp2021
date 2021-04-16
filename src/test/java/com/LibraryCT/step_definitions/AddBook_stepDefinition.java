package com.LibraryCT.step_definitions;

import com.LibraryCT.base.LibraryAppBaseTest;
import com.LibraryCT.pages.LibrarianBookPage;
import com.LibraryCT.pages.LibrarianDashBoardPage;
import com.LibraryCT.pages.LoginPage;
import com.LibraryCT.pojo.BookPojo;
import com.LibraryCT.utilities.BrowserUtils;
import com.LibraryCT.utilities.DB_Utility;
import com.LibraryCT.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


import java.util.Map;

public class AddBook_stepDefinition extends LibraryAppBaseTest {
BookPojo bookPojo;
LoginPage loginPage=new LoginPage();
Map<String, Object> newBookDataMap;
LibrarianDashBoardPage librarianDashBoardPage=new LibrarianDashBoardPage();
LibrarianBookPage librarianBookPage=new LibrarianBookPage();
String token=LibraryAppBaseTest.getToken("librarian69@library", "KNPXrm3S");
int newBookId;



    @Given("user POST - Add a book on API")

    public void user_post_add_a_book_on_api(io.cucumber.datatable.DataTable dataTable) {

        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        newBookDataMap=dataTable.asMap(String.class, Object.class);

        newBookId=
                given()
                .header("x-library-token",token)
                .formParams(newBookDataMap).
                        when()
                .post("/add_book").
                        then()

                .statusCode(200)
                .log().body()
                .extract()
                .jsonPath().getInt("book_id") ;
        System.out.println("newBookId = " + newBookId);


    }

    @When("the user is logged in and on the Book page")
    public void theUserIsLoggedInAndOnTheBookPage() {
        BrowserUtils.sleep(3);
        loginPage.loginToLibraryApp_Librarian();
        BrowserUtils.sleep(3);
        librarianDashBoardPage.bookFunctionality.click();
    }




    @When("the user searches book by {string}")
    public void theUserSearchesBookBy(String search) {
        librarianBookPage.searchBox.sendKeys(search+ Keys.ENTER);
    }

    @And("the user validates UI and API info about a created book")
    public void theUserValidatesUIAndAPIInfoAboutACreatedBook() {
        //UI_BookName=librarianBookPage.bookName.getText();
        BrowserUtils.sleep(5);
       // assertThat(newBookDataMap.get("name"), is(librarianBookPage.bookName.getText()));

        bookPojo=
                given()

                        .header("x-library-token",token)
                        .pathParam("id", newBookId).

                        when()
                        .get("/get_book_by_id/{id}")

                        .as(BookPojo.class);

        assertThat(bookPojo.getName(), is(librarianBookPage.bookName.getText()));

    }

    @Then("the user connects with DB, validate API and DB info about book")
    public void theUserConnectsWithDBValidateAPIAndDBInfoAboutBook() {
        DB_Utility.createConnection();
       DB_Utility.runQuery("SELECT * FROM books where id = " + newBookId ) ;

        Map<String,String> dbResultMap =  DB_Utility.getRowMap(1) ;
        System.out.println("dbResultMap = " + dbResultMap);

        //randomBookMapBody = {year=1682, author=Myrtis Barrows IV, isbn=71104517, name=Noli Me Tangere, description=There is nothing regular about Chuck Norris' expressions., book_category_id=13}
        //dbResultMap =       {id=3133, name=Noli Me Tangere, isbn=71104517, year=1682, author=Myrtis Barrows IV, book_category_id=13, description=There is nothing regular about Chuck Norris' expressions., added_date=2021-04-09 18:54:16}
        assertThat(dbResultMap.get("name") , is(newBookDataMap.get("name"))   ) ;
        // keep going and do the rest , or find a better way.

    }


}
