Feature: Transactions

  Scenario: Transfer money
    Given Two accounts exist for user "transactionuser"
    And I am authenticated for transactions operations as "transactionuser"
    When I send a POST request for transactions to "/accounts/v1/transactions/transfer" with body
      """
      {
        "sourceAccount": "SRC123",
        "destinationAccount": "DST123",
        "amount": 200
      }
      """
    Then the transactions response status should be 200
