Feature: check controller test
I want to use this to check controller

Scenario: Requesting health check
Given I have postmand
When I send reqeust
Then I receive message "Working"