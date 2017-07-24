package com.itv.checkout;

import com.itv.checkout.domain.Item;
import com.itv.checkout.domain.MultibuyOfferRule;
import com.itv.checkout.domain.SpecialOffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by raimon on 24/07/2017.
 */
final class MultibuySpecialOffersProcessor implements SpecialOffersProcessor {
    private Collection<MultibuyOfferRule> offerRules;

    MultibuySpecialOffersProcessor(Collection<MultibuyOfferRule> offerRules) {
        this.offerRules = new ArrayList<>(offerRules);
    }

    @Override
    public List<SpecialOffer> getAllApplicable(List<Item> items) {
        return offerRules.stream()
                .map(offerRule -> offerRule.createOffers(items))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
