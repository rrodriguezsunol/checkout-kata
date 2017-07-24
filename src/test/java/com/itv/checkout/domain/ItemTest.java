package com.itv.checkout.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Created by raimon on 24/07/2017.
 */
public class ItemTest {

    @Test
    public void newInstanceWithNullSkuThrowsException() {

        Throwable caughtException = catchThrowable(() -> new Item(null, 35));

        assertThat(caughtException).isExactlyInstanceOf(NullPointerException.class)
                .hasMessage("sku cannot be null");
    }

    @Test
    public void newInstanceWithPriceLessThanOneThrowsException() {

        Throwable caughtException = catchThrowable(() -> new Item("sku", 0));

        assertThat(caughtException).isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("price cannot be less than 1");
    }
}