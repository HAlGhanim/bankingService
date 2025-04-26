Feature: Authentication

  Scenario: User registration
    When I send a POST request for authentication to "/auth/register" with body
      """
      {
        "username": "newuser",
        "password": "newpassword"
      }
      """
    Then the authentication response status should be 201

  Scenario: User login and get JWT token
    Given A user with username "loginuser" and password "loginpassword" exists
    When I send a POST request for authentication to "/auth/login" with body
      """
      {
        "username": "loginuser",
        "password": "loginpassword"
      }
      """
    Then the authentication response status should be 200
    And the authentication response body should not be empty
