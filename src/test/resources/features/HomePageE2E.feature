@wip
Feature: Test - UI - Parallel 2

   @regression
  Scenario: HomePage - Flow 1
    Given User is on the HomePage from "/"
    When User taps on the appButton field

   @regression
  Scenario: HomePage - Flow 2
    Given User is on the HomePage from "/"
    When User taps on the app2Button field

   @regression
  Scenario: HomePage - Flow 3
    Given User is on the HomePage from "/"
    When User taps on the textButton field
