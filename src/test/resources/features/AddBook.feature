Feature: Librarian add book feature

	
	@G24-180

		Scenario: As a librarian, I should be able to add books on API and validate with UI-DB

			Given user POST - Add a book on API
		    |name|Summer days|
		    |isbn|80807867|
		    |year|2021|
		    |author|Chuck Norris|
		    |book_category_id|4|
		    |description|Summer jelly bean sweat rain and train.|
		    When the user is logged in and on the Book page
		    And the user searches book by "Summer days"
		    And the user validates UI and API info about a created book
		    Then the user connects with DB, validate API and DB info about book


