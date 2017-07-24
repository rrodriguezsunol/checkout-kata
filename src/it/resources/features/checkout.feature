Feature: Checkout

Scenario: Checkout different items
    Given we start a checkout transaction
    And we scan the following items:
        | D |
        | A |
        | B |
        | C |
    When we ask for the total price
    Then the total price is 115p

  Scenario: Apply special offers
    Given we start a checkout transaction
    And we scan the following items:
        | A |
        | B |
        | A |
        | B |
        | A |
        | C |
    When we ask for the total price
    Then the total price is 195p