package com.itv.checkout;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.itv.checkout.domain.Item;
import com.itv.checkout.domain.MultibuyOfferRule;
import com.itv.checkout.repository.InMemoryItemRepository;
import com.itv.checkout.repository.ItemRepository;
import cucumber.api.DataTable;
import cucumber.api.java8.En;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by raimon on 26/03/2017.
 */
public class CheckoutSteps implements En {

    private Checkout checkout;

    private Integer totalPriceInPence;


    public CheckoutSteps() {
        Given("^we start a checkout transaction$", () -> {
            Item itemA = new Item("A", 50);
            Item itemB = new Item("B", 30);
            Item itemC = new Item("C", 20);
            Item itemD = new Item("D", 15);
            List<Item> listOfItems = Arrays.asList(itemA, itemB, itemC, itemD);

            Map<String, Item> itemSkuPriceMap = listOfItems.stream()
                    .collect(Collectors.toMap(Item::getSku, item -> item));
            ItemRepository itemRepository = new InMemoryItemRepository(itemSkuPriceMap);

            SpecialOffersProcessor specialOffersProcessor = new MultibuySpecialOffersProcessor(asList(
                    new MultibuyOfferRule(itemA, 3, 20),
                    new MultibuyOfferRule(itemB, 2, 15)));

            checkout = new Checkout(itemRepository, specialOffersProcessor);
        });

        Given("^we scan the following items:$", (DataTable scannedItems) -> {
            scannedItems.asList(String.class).stream().forEach(itemSku -> checkout.scan(itemSku));
        });

        When("^we ask for the total price$", () -> totalPriceInPence = checkout.getTotal());

        Then("^the total price is (\\d+)p$",
                (Integer expectedTotalPrice) -> assertThat(totalPriceInPence).isEqualTo(expectedTotalPrice));
    }
}
