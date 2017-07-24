package com.itv.checkout;

import com.itv.checkout.domain.Item;
import com.itv.checkout.domain.SpecialOffer;

import java.util.List;

/**
 * Created on 24/07/2017.
 */
public interface SpecialOffersProcessor {

    List<SpecialOffer> getAllApplicable(List<Item> items);
}
