Feature: Integration test

  Scenario: Call an endpoint
    Given the user is authenticated with "user" and "password"
    When the client makes a GET request to "/data/jpa/1"
    Then the HTTP status code should be 200
