package com.itv.checkout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Responsibilities:
 * 1. it creates the pricing of the items.
 * 3. it stores scanned items.
 * 2. it calculates the total by adding the price of each item.
 *
 * Created by raimon on 23/07/2017.
 */
public class Checkout {
    private Map<String, Integer> itemSkuPriceMap;
    private List<String> scannedItemSkus;

    public Checkout() {
        itemSkuPriceMap = new HashMap<>();
        itemSkuPriceMap.put("A", 50);
        itemSkuPriceMap.put("B", 30);
        itemSkuPriceMap.put("C", 20);
        itemSkuPriceMap.put("D", 15);

        scannedItemSkus = new ArrayList<>();
    }

    public int getTotal() {
        int total = 0;

        for (String itemSku : scannedItemSkus) {
            total += itemSkuPriceMap.get(itemSku);
        }

        return total;
    }

    public void scan(String itemSku) {
        Objects.requireNonNull(itemSku, "itemSku cannot be null");

        if (itemSkuPriceMap.get(itemSku) == null) {
            throw new IllegalArgumentException(String.format("No item with SKU '%s' exists", itemSku));
        }

        scannedItemSkus.add(itemSku);
    }
}
