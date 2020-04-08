Feature: The phone book must be consistent

  Scenario: phone book has no entries
    Given a phone book
    When there are no entries
    Then the phone book is consistent

  Scenario: phone book with one entry
    Given a phone book
    When there is a entry "BOB 123"
    Then the phone book is consistent

  Scenario: phone book with duplicate entries
    Given a phone book
    When there is a entry "BOB 123", "BOB 123"
    Then the phone book is in "prefix error"

  Scenario: phone book 2 different entries
    Given a phone book
    When there is a entry "BOB 123", "JOHN 456"
    Then the phone book is consistent


  Scenario: phone book of 1 number being prefix of another
    Given a phone book
    When there is a entry "BOB 123", "JOHN 123456"
    Then the phone book is in "prefix error"


  Scenario: phone book of 1 number being prefix of another
    Given a phone book
    When there is a entry "BOB 123456", "JOHN 123"
    Then the phone book is in "prefix error"


  Scenario: phone book of 1 number being prefix of another with extra spaces
    Given a phone book
    When there is a entry "BOB 12 3", "JOHN 124567"
    Then the phone book is consistent