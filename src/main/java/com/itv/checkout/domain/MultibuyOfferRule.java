package com.itv.checkout.domain;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created on 24/07/2017.
 */
public class MultibuyOfferRule {
    private final Item discountedItem;
    private final int requiredNumberOfItems;
    private final int discountAmount;

    public MultibuyOfferRule(Item discountedItem, int requiredNumberOfItems, int discountAmount) {
        Objects.requireNonNull(discountedItem, "discountedItem cannot be null");

        if (requiredNumberOfItems < 2) {
            throw new IllegalArgumentException("requiredNumberOfItems cannot be less than 2");
        }

        if (discountAmount < 1) {
            throw new IllegalArgumentException("discountAmount cannot be less than 1");
        }

        this.discountedItem = discountedItem;
        this.requiredNumberOfItems = requiredNumberOfItems;
        this.discountAmount = discountAmount;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public List<SpecialOffer> createOffers(List<Item> items) {
        long occurrencesOfDiscountedItem = items.stream().filter(discountedItem::equals).count();

        long numberOfSpecialOffers = occurrencesOfDiscountedItem / requiredNumberOfItems;

        return Stream.generate(() -> new SpecialOffer(discountAmount))
                .limit(numberOfSpecialOffers)
                .collect(Collectors.toList());
    }
}
