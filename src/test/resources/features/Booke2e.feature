@wip
Feature: Librarian add book feature
  @ww
  Scenario: As a librarian, I should be able to add books on API and validate with UI-DB
    Given user creates a book with api endpoint "/add_book"
    When user login as librarian
    Then user should verify that the book is created
    Then user should verify that the book is created in database as well
