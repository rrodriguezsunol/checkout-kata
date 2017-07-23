package com.itv.checkout.repository;

import com.itv.checkout.domain.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by raimon on 23/07/2017.
 */
public class InMemoryItemRepository implements ItemRepository {
    private final Map<String, Item> items;

    public InMemoryItemRepository(Map<String, Item> items) {
        this.items = new HashMap<>(items);
    }

    @Override
    public Optional<Item> getBySku(String itemSku) {
        Objects.requireNonNull(itemSku, "itemSku cannot be null");

        return Optional.ofNullable(items.get(itemSku));
    }
}
