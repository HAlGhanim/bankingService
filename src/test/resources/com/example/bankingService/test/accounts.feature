Feature: Account Management

  Scenario: Create an account
    Given A user with username "accountuser" and password "accountpass" exists
    And I am authenticated for accounts operations as "accountuser"
    When I send a POST request for accounts to "/accounts/v1/create" with body
      """
      {
        "userId": 1,
        "balance": 1000,
        "isActive": true,
        "accountNumber": "ACC12345",
        "name": "Primary Account"
      }
      """
    Then the accounts response status should be 200

  Scenario: List accounts
    Given I am authenticated for accounts operations as "accountuser"
    When I send a GET request for accounts to "/accounts/v1/"
    Then the accounts response status should be 200

  Scenario: Close an account
    Given I am authenticated for accounts operations as "accountuser"
    When I send a POST request for accounts to "/accounts/v1/ACC12345/close" with empty body
    Then the accounts response status should be 204
