package com.itv.checkout.domain;

/**
 * Created by raimon on 23/07/2017.
 */
public final class Item {
    private final String sku;
    private final int price;

    public Item(String sku, int price) {
        this.sku = sku;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
