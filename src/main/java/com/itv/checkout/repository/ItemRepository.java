package com.itv.checkout.repository;

import com.itv.checkout.domain.Item;

import java.util.Optional;

/**
 * Created by raimon on 23/07/2017.
 */
public interface ItemRepository {

    Optional<Item> getBySku(String sku);
}
