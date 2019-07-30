Feature: Cargo

# Executes before each scenario to check service health status
# Background: Check Service health status

 Scenario: create a cargo
    When an API call is executed: POST 'http://cargo:8080/api/cargo' with body:
    """
      {
        "text":"fakeCargo"
      }
    """
    Then response status should be 200
    And response body contains "fakeCargo"


 Scenario: get all cargo items
    When an API call is executed: GET 'http://cargo:8080/api/cargo'
    Then response status should be 200

 Scenario: delete cargo
     Given id after an API call is executed: GET 'http://cargo:8080/api/cargo'
     When an API call is executed: DELETE 'http://cargo:8080/api/cargo'
     Then response status should be 200
     And response body contains "[]"