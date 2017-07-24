package com.itv.checkout;

import com.itv.checkout.domain.Item;
import com.itv.checkout.domain.SpecialOffer;
import com.itv.checkout.repository.ItemRepository;
import junitparams.JUnitParamsRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Created by raimon on 23/07/2017.
 */
@RunWith(JUnitParamsRunner.class)
public class CheckoutTest {

    private Checkout checkout;

    private ItemRepository mockItemRepository = mock(ItemRepository.class);
    private SpecialOffersProcessor mockSpecialOffersProcessor = mock(SpecialOffersProcessor.class);

    private final Item itemOne = new Item("item-1", 50);

    @Before
    public void initCheckout() {
        checkout = new Checkout(mockItemRepository, mockSpecialOffersProcessor);
    }

    @Test
    public void scanNoItemsReturnsZeroAsTotal() {

        int actualTotal = checkout.getTotal();

        assertThat(actualTotal).isZero();
    }

    @Test
    public void scanOneItemReturnsItemsPrice() {
        given(mockItemRepository.getBySku(itemOne.getSku())).willReturn(Optional.of(itemOne));

        checkout.scan(itemOne.getSku());
        int actualTotal = checkout.getTotal();

        assertThat(actualTotal).isEqualTo(itemOne.getPrice());
    }

    @Test
    public void scanManyItemsReturnsSumOfItemsPrices() {
        given(mockItemRepository.getBySku(itemOne.getSku())).willReturn(Optional.of(itemOne));

        String itemTwoSku = "item-2";
        int itemTwoPrice = 40;
        given(mockItemRepository.getBySku(itemTwoSku)).willReturn(Optional.of(new Item(itemTwoSku, itemTwoPrice)));

        checkout.scan(itemOne.getSku());
        checkout.scan(itemTwoSku);

        int actualTotal = checkout.getTotal();

        assertThat(actualTotal).isEqualTo(90);
    }

    @Test
    public void scanThrowsExceptionWhenNoItemWithSuchSkuExists() {
        String invalidSku = "invalid-sku";
        given(mockItemRepository.getBySku(invalidSku)).willReturn(Optional.empty());

        Throwable caughtException = catchThrowable(() -> checkout.scan(invalidSku));

        assertThat(caughtException).isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("No item with SKU 'invalid-sku' exists");
    }

    @Test
    public void scanAppliesNoOffersWhenThereAreNoneApplicable() {
        given(mockItemRepository.getBySku(itemOne.getSku())).willReturn(Optional.of(itemOne));

        given(mockSpecialOffersProcessor.getAllApplicable(singletonList(itemOne))).willReturn(emptyList());

        checkout.scan(itemOne.getSku());

        int actualTotal = checkout.getTotal();

        assertThat(actualTotal).isEqualTo(itemOne.getPrice());
    }

    @Test
    public void scanAppliesOneOfferWhenSpecialOffersReturnsTheOffer() {
        given(mockItemRepository.getBySku(itemOne.getSku())).willReturn(Optional.of(itemOne));

        SpecialOffer mockSpecialOfferOne = mock(SpecialOffer.class);
        given(mockSpecialOfferOne.getDiscountAmount()).willReturn(35);

        given(mockSpecialOffersProcessor.getAllApplicable(singletonList(itemOne))).willReturn(singletonList(mockSpecialOfferOne));

        checkout.scan(itemOne.getSku());

        int actualTotal = checkout.getTotal();

        assertThat(actualTotal).isEqualTo(15);
    }

    @Test
    public void scanAppliesAllOffersThatAreApplicable() {
        given(mockItemRepository.getBySku(itemOne.getSku())).willReturn(Optional.of(itemOne));

        SpecialOffer mockSpecialOfferOne = mock(SpecialOffer.class);
        given(mockSpecialOfferOne.getDiscountAmount()).willReturn(15);
        SpecialOffer mockSpecialOfferTwo = mock(SpecialOffer.class);
        given(mockSpecialOfferTwo.getDiscountAmount()).willReturn(10);

        given(mockSpecialOffersProcessor.getAllApplicable(singletonList(itemOne))).willReturn(asList(mockSpecialOfferOne, mockSpecialOfferTwo));

        checkout.scan(itemOne.getSku());

        int actualTotal = checkout.getTotal();

        assertThat(actualTotal).isEqualTo(25);
    }

    // Todo: add edge case test when the total price is greater than MAX INTEGER

    // Todo: add edge case test when the total discount is greater than MAX INTEGER
}