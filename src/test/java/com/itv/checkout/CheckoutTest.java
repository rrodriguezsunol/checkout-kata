package com.itv.checkout;

import com.itv.checkout.domain.Item;
import com.itv.checkout.repository.ItemRepository;
import junitparams.JUnitParamsRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Created by raimon on 23/07/2017.
 */
@RunWith(JUnitParamsRunner.class)
public class CheckoutTest {

    private ItemRepository mockItemRepository = mock(ItemRepository.class);
    private Checkout checkout;

    @Before
    public void initCheckout() {
        checkout = new Checkout(mockItemRepository);
    }

    @Test
    public void scanNoItemsReturnsZeroAsTotal() {

        int actualTotal = checkout.getTotal();

        assertThat(actualTotal).isZero();
    }

    @Test
    public void scanOneItemReturnsItemsPrice() {
        String itemSku = "item-1";
        int itemPrice = 50;
        given(mockItemRepository.getBySku(itemSku)).willReturn(Optional.of(new Item(itemSku, itemPrice)));

        checkout.scan(itemSku);
        int actualTotal = checkout.getTotal();

        assertThat(actualTotal).isEqualTo(itemPrice);
    }

    @Test
    public void scanManyItemsReturnsSumOfItemsPrices() {
        String itemOneSku = "item-1";
        int itemOnePrice = 20;
        given(mockItemRepository.getBySku(itemOneSku)).willReturn(Optional.of(new Item(itemOneSku, itemOnePrice)));

        String itemTwoSku = "item-2";
        int itemTwoPrice = 40;
        given(mockItemRepository.getBySku(itemTwoSku)).willReturn(Optional.of(new Item(itemTwoSku, itemTwoPrice)));

        checkout.scan(itemOneSku);
        checkout.scan(itemTwoSku);

        int actualTotal = checkout.getTotal();

        assertThat(actualTotal).isEqualTo(60);
    }

    @Test
    public void scanThrowsExceptionWhenNoItemWithSuchSkuExists() {
        String invalidSku = "invalid-sku";
        given(mockItemRepository.getBySku(invalidSku)).willReturn(Optional.empty());

        Throwable caughtException = catchThrowable(() -> checkout.scan(invalidSku));

        assertThat(caughtException).isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("No item with SKU 'invalid-sku' exists");
    }
}