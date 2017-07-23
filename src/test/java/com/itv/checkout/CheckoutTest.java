package com.itv.checkout;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Created by raimon on 23/07/2017.
 */
@RunWith(JUnitParamsRunner.class)
public class CheckoutTest {

    private final Checkout checkout = new Checkout();

    @Test
    public void scanNoItemsReturnsZeroAsTotal() {

        int actualTotal = checkout.getTotal();

        assertThat(actualTotal).isZero();
    }

    @Test
    @Parameters(method = "itemsPricing")
    public void scanOneItemReturnsItemsPrice(String itemSku, int itemPrice) {
        checkout.scan(itemSku);

        int actualTotal = checkout.getTotal();

        assertThat(actualTotal).isEqualTo(itemPrice);
    }

    private Object[] itemsPricing() {
        // Todo: now we have duplication between test and production code
        return new Object[] {
                new Object[] {"A", 50},
                new Object[] {"B", 30},
                new Object[] {"C", 20},
                new Object[] {"D", 15}
        };
    }

    @Test
    public void scanThrowsExceptionWhenSkuIsNull() {
        Checkout checkout = new Checkout();

        Throwable caughtException = catchThrowable(() -> checkout.scan(null));

        assertThat(caughtException).isExactlyInstanceOf(NullPointerException.class)
                .hasMessage("itemSku cannot be null");
    }

    @Test
    public void scanThrowsExceptionWhenNoItemWithSuchSkuExists() {
        Checkout checkout = new Checkout();

        Throwable caughtException = catchThrowable(() -> checkout.scan("invalid-sku"));

        assertThat(caughtException).isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("No item with SKU 'invalid-sku' exists");
    }
}