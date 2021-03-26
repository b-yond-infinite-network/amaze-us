@stage2
Feature: Stage2
  In order to achieve stage 2, the booster's tanks needs to be empty

  Background:
    Given 2 tanks created with fuel

  Scenario: Stage 2 can't be achieve, booster still has fuel
    When trying to execute stage 2
    Then stage2 app logs "Booster still has fuel, not released"

  Scenario: Stage 2 achieved
    Given all the fuel was consumed
    When trying to execute stage 2
    Then stage2 app logs "No more fuel, released"

