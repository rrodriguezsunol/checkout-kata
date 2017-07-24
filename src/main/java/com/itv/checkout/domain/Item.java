package com.itv.checkout.domain;

import java.util.Objects;

/**
 * Created by raimon on 23/07/2017.
 */
public final class Item {
    private final String sku;
    private final int price;

    public Item(String sku, int price) {
        Objects.requireNonNull(sku, "sku cannot be null");

        if (price < 1) {
            throw new IllegalArgumentException("price cannot be less than 1");
        }

        this.sku = sku;
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public int getPrice() {
        return price;
    }
}
