package com.itv.checkout;

import java.util.List;

import com.itv.checkout.domain.Item;
import com.itv.checkout.domain.SpecialOffer;

/**
 * Created on 24/07/2017.
 */
public interface SpecialOffersProcessor {

    List<SpecialOffer> getApplicable(List<Item> items);
}
