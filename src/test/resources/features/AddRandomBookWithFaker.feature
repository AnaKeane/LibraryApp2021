Feature: Add Book Feature
  @www
  Scenario: As a librarian, I should be able to add books
  on API and validate on UI,DB
    Given user add a new book with API
    When user validate new book is in DataBase
    Then user could search book by name on LibraryCT app