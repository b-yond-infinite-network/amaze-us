Feature: Tanks

# Executes before each scenario to check service health status
Background: Check Service health status
    Given Request URL is "url"
    When Sending a "GET" request 
    Then Expected response is "response"

@IntegrationTest
Scenario: CreateTank
	Given Request URL is "url"	
	When Sending a "POST" request with payload "payload"
	Then Expected response is "response"
	
@RegressionTest
Scenario: GetTank
	Given Request URL is "url"	
	When Sending a "GET" request 
	Then Expected response is "response"  
	
Scenario: UpdateTank
	Given Request URL is "url"	
	When Sending a "PUT" request with payload "payload"
	Then Expected response is "response" 

@SmokeTest
Scenario: DeleteTank
	Given Request URL is "url"	
	When Sending a "DELETE" request 
	Then Expected response is "response"
	
Scenario: GetAllTanks
	Given Request URL is "url"	
	When Sending a "GET" request 
	Then Expected response is "response"

@WebTest
Scenario: ArchiveTank
	Given Request URL is "url"	
	When Sending a "PUT" request 
	Then Expected response is "response"
	
Scenario: RestoreTank
	Given Request URL is "url"	
	When Sending a "DELETE" request 
	Then Expected response is "response"
	
# This scenario is used to run same test for multiple services with the usage of data table
@HealthCheckTest
Scenario Outline: Check all services health status
	Given Request URL is "<url>"	
	When Sending a "GET" request
	Then Expected response is "<response>" 
	
	Examples: 
		| url | response |
		| http://localhost:1234 | {status: OK} |	
	
