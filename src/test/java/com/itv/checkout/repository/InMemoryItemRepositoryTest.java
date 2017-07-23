package com.itv.checkout.repository;

import org.junit.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Created by raimon on 23/07/2017.
 */
public class InMemoryItemRepositoryTest {

    @Test
    public void scanThrowsExceptionWhenSkuIsNull() {
        InMemoryItemRepository itemRepository = new InMemoryItemRepository(new HashMap<>());

        Throwable caughtException = catchThrowable(() -> itemRepository.getBySku(null));

        assertThat(caughtException).isExactlyInstanceOf(NullPointerException.class)
                .hasMessage("itemSku cannot be null");
    }

}