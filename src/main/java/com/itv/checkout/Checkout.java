package com.itv.checkout;

import java.util.ArrayList;
import java.util.List;

import com.itv.checkout.domain.Item;
import com.itv.checkout.domain.SpecialOffer;
import com.itv.checkout.repository.ItemRepository;

/**
 * Responsibilities:
 * 1. it stores scanned items.
 * 2. it calculates the total amount by:
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

    /**
     * Returns the total amount (in pence) of the items purchased, with the special offers applied.
     *
     * @return the total amount (in pence) after applying all the offers, of the items purchased.
     */
    public int getTotal() {
        int totalItemPrices = scannedItems.stream().mapToInt(Item::getPrice).sum();

        List<SpecialOffer> applicableOffers = specialOffersProcessor.getAllApplicable(scannedItems);

        int discounts = applicableOffers.stream().mapToInt(SpecialOffer::getDiscountAmount).sum();

        return totalItemPrices - discounts;
    }

    /**
     * Scans an item and adds it to the checkout list.
     * @param itemSku the item's sku to be scanned and added to the list.
     * @throws IllegalArgumentException if the item isn't found.
     */
    public void scan(String itemSku) {
        Item item = itemRepository.getBySku(itemSku)
                .orElseThrow(() -> new IllegalArgumentException(String.format("No item with SKU '%s' exists", itemSku)));

        scannedItems.add(item);
    }
}
