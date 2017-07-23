package com.itv.checkout;

import com.itv.checkout.domain.Item;
import com.itv.checkout.repository.InMemoryItemRepository;
import cucumber.api.java8.En;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by raimon on 26/03/2017.
 */
public class CheckoutSteps implements En {

    private Checkout checkout;

    private Integer totalPriceInPence;


    public CheckoutSteps() {
        Given("^we start a checkout transaction$", () -> {
            Map<String, Item> itemSkuPriceMap = new HashMap<>();
            itemSkuPriceMap.put("A", new Item("A", 50));
            itemSkuPriceMap.put("B", new Item("B", 30));
            itemSkuPriceMap.put("C", new Item("C", 20));
            itemSkuPriceMap.put("D", new Item("D", 15));

            checkout = new Checkout(new InMemoryItemRepository(itemSkuPriceMap));
        });

        Given("^item \"([^\"]*)\" is scanned$", (String itemSku) -> {
            checkout.scan(itemSku);
        });

        When("^we ask for the total price$", () -> totalPriceInPence = checkout.getTotal());

        Then("^the total price is (\\d+)p$",
                (Integer expectedTotalPrice) -> assertThat(totalPriceInPence).isEqualTo(expectedTotalPrice));
    }
}
