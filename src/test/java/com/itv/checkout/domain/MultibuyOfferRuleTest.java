package com.itv.checkout.domain;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Created on 24/07/2017.
 */
public class MultibuyOfferRuleTest {

    private Item itemWithOffer = new Item("item-1", 20);
    private Item anotherItem = new Item("another-item", 49);
    private int numberOfItems = 2;
    private int discountAmount = 45;

    private MultibuyOfferRule multibuyOfferRule = new MultibuyOfferRule(itemWithOffer, numberOfItems, discountAmount);

    @Test
    public void createInstanceThrowsExceptionWhenItemIsNull() {
        Throwable caughtException = catchThrowable(() -> new MultibuyOfferRule(null, 25, 25));

        assertThat(caughtException).isExactlyInstanceOf(NullPointerException.class)
                .hasMessage("discountedItem cannot be null");
    }

    @Test
    public void createInstanceWithOneNumberOfItemsThrowsException() {

        Throwable caughtException = catchThrowable(() -> new MultibuyOfferRule(itemWithOffer, 1, 25));

        assertThat(caughtException).isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("numberOfItems cannot be less than 2");
    }

    @Test
    public void createInstanceWithZeroDiscountAmountThrowsException() {

        Throwable caughtException = catchThrowable(() -> new MultibuyOfferRule(itemWithOffer, 2, 0));

        assertThat(caughtException).isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("discountAmount cannot be less than 1");
    }

    @Test
    public void createOffersReturnsZeroWhenThereAreNotEnoughItems() {
        MultibuyOfferRule multibuyOfferRule = new MultibuyOfferRule(itemWithOffer, 2, 1);

        List<SpecialOffer> actualOffers = multibuyOfferRule.createOffers(singletonList(itemWithOffer));

        assertThat(actualOffers).isEmpty();
    }

    @Test
    public void createOffersReturnsZeroWhenThereAreNoRulesForTheSpecifiedItem() {
        MultibuyOfferRule multibuyOfferRule = new MultibuyOfferRule(itemWithOffer, 2, 1);

        List<SpecialOffer> actualOffers = multibuyOfferRule.createOffers(singletonList(anotherItem));

        assertThat(actualOffers).isEmpty();
    }

    @Test
    public void createOffersReturnsOneInstanceWhenTheNumberOfItemsSatisfiesTheRuleOnce() {

        List<SpecialOffer> actualOffers = multibuyOfferRule.createOffers(asList(itemWithOffer, itemWithOffer));

        assertThat(actualOffers).hasSize(1);
        assertThat(actualOffers.get(0).getDiscountAmount()).isEqualTo(multibuyOfferRule.getDiscountAmount());
    }

    @Test
    public void createOffersReturnsTwoInstancesWhenTheNumberOfItemsSatisfiesTheRuleTwice() {

        List<SpecialOffer> actualOffers = multibuyOfferRule.createOffers(
                asList(itemWithOffer, itemWithOffer, itemWithOffer, itemWithOffer));

        assertThat(actualOffers).hasSize(2);
        assertThat(actualOffers).extracting(SpecialOffer::getDiscountAmount)
                .containsExactly(discountAmount, discountAmount);
    }

    @Test
    public void createOffersReturnsInstanceWhenTheNumberOfItemsSatisfiesTheRuleAndTheItemIsMixedWithOtherItem() {

        List<SpecialOffer> actualOffers = multibuyOfferRule.createOffers(
                asList(anotherItem, itemWithOffer, anotherItem, anotherItem, itemWithOffer));

        assertThat(actualOffers).hasSize(1);
        assertThat(actualOffers.get(0).getDiscountAmount()).isEqualTo(multibuyOfferRule.getDiscountAmount());
    }
}