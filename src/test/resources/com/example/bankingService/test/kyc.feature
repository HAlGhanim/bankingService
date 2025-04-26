Feature: KYC Management

  Scenario: Create KYC Profile
    Given A user with username "kycuser" and password "kycpass" exists
    And I am authenticated for KYC operations as "kycuser"
    When I send a POST request for kyc to "/kyc/v1/create" with body
      """
      {
        "userId": 1,
        "firstName": "John",
        "lastName": "Doe",
        "nationality": "Kuwaiti",
        "dateOfBirth": "1990-01-01",
        "salary": 5000
      }
      """
    Then the kyc response status should be 200

  Scenario: Fetch KYC Profile
    Given I am authenticated for KYC operations as "kycuser"
    When I send a GET request for kyc to "/kyc/v1/1"
    Then the kyc response status should be 200
