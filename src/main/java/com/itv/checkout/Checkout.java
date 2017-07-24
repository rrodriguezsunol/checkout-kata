package com.itv.checkout;

import com.itv.checkout.domain.Item;
import com.itv.checkout.domain.SpecialOffer;
import com.itv.checkout.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsibilities:
 * 1. it stores scanned items.
 * 2. it calculates the total amout by:
 *      a. adding the price of each item together.
 *      b. subtracting the discount amounts of the offers from the total
 *
 * Created by raimon on 23/07/2017.
 */
public class Checkout {
    private ItemRepository itemRepository;
    private SpecialOffersProcessor specialOffersProcessor;
    private List<Item> scannedItems;

    public Checkout(ItemRepository itemRepository, SpecialOffersProcessor specialOffersProcessor) {
        this.itemRepository = itemRepository;
        this.specialOffersProcessor = specialOffersProcessor;
        scannedItems = new ArrayList<>();
    }

    public int getTotal() {
        int totalItemPrices = scannedItems.stream().mapToInt(Item::getPrice).sum();

        List<SpecialOffer> applicableOffers = specialOffersProcessor.getAllApplicable(scannedItems);

        int discounts = applicableOffers.stream().mapToInt(SpecialOffer::getDiscountAmount).sum();

        return totalItemPrices - discounts;
    }

    public void scan(String itemSku) {
        Item item = itemRepository.getBySku(itemSku)
                .orElseThrow(() -> new IllegalArgumentException(String.format("No item with SKU '%s' exists", itemSku)));

        scannedItems.add(item);
    }
}
