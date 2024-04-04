package com.dendeberia.facebookassignment.dto;

import com.facebook.ads.sdk.AdSet;
import com.facebook.ads.sdk.Campaign;

import java.util.List;

public record CreateAdvertisementDto(String name, String title, String body,
                                     Campaign.EnumObjective enumObjective, AdSet.EnumBillingEvent enumBillingEvent,
                                     Long spendCap, Long dailyBudget,
                                     String link, List<String> targetingCountries, Long targetingAgeMin,
                                     Long targetingAgeMax) {
}
