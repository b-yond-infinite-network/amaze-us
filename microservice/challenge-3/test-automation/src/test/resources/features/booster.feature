Feature: Booster

# Executes before each scenario to check service health status
# Background: Check Service health status

 Scenario: create a tank
    When an API call is executed: POST 'http://booster:3000/tanks' with body:
    """
      {
        "title":"reverseTank",
        "archived": true,
        "fuel": [{"title":"part1", "priority":"1", "done": true, "deadline":"2020-01-02T15:04:05Z"}]
      }
    """
    Then response status should be 201
    And response body contains "reverseTank"


 Scenario: get all tanks
    When an API call is executed: GET 'http://booster:3000/tanks'
    Then response status should be 200
    And response body contains "reverseTank"

 Scenario: delete tank
     When an API call is executed: DELETE 'http://booster:3000/tanks/reverseTank'
     Then response status should be 204
