package com.itv.checkout;

import cucumber.api.java8.En;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by raimon on 26/03/2017.
 */
public class CheckoutSteps implements En {

    private Checkout checkout;

    private Integer totalPriceInPence;


    public CheckoutSteps() {
        Given("^we start a checkout transaction$", () -> checkout = new Checkout());

        Given("^item \"([^\"]*)\" is scanned$", (String itemSku) -> {
            checkout.scan(itemSku);
        });

        When("^we ask for the total price$", () -> totalPriceInPence = checkout.getTotal());

        Then("^the total price is (\\d+)p$",
                (Integer expectedTotalPrice) -> assertThat(totalPriceInPence).isEqualTo(expectedTotalPrice));
    }
}
