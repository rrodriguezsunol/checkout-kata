package com.itv.checkout.domain;

/**
 * Created on 24/07/2017.
 */
public class SpecialOffer {
    private int discountAmount;

    SpecialOffer(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public String toString() {
        return "SpecialOffer{" +
                "discountAmount=" + discountAmount +
                '}';
    }
}
