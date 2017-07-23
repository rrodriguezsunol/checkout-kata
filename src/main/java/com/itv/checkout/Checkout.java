package com.itv.checkout;

import com.itv.checkout.domain.Item;
import com.itv.checkout.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsibilities:
 * 1. it stores scanned items.
 * 2. it calculates the total by adding the price of each item.
 *
 * Created by raimon on 23/07/2017.
 */
public class Checkout {
    private ItemRepository itemRepository;
    private List<Item> scannedItems;

    public Checkout(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        scannedItems = new ArrayList<>();
    }

    public int getTotal() {
        int total = 0;

        for (Item item : scannedItems) {
            total += item.getPrice();
        }

        return total;
    }

    public void scan(String itemSku) {
        Item item = itemRepository.getBySku(itemSku)
                .orElseThrow(() -> new IllegalArgumentException(String.format("No item with SKU '%s' exists", itemSku)));

        scannedItems.add(item);
    }
}
