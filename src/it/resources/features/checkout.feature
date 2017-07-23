Feature: Checkout

  Scenario: Checkout different items
    Given we start a checkout transaction
    And item "D" is scanned
    And item "A" is scanned
    And item "B" is scanned
    And item "C" is scanned
    When we ask for the total price
    Then the total price is 115p