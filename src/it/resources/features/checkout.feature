Feature: Checkout

  Scenario: No items checkout
    Given we start a checkout transaction
    And no item has been scanned
    When we ask for the total price
    Then 0 is returned