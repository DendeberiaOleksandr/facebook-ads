package com.dendeberia.facebookassignment.service.impl;

import com.dendeberia.facebookassignment.dto.CreateAdvertisementDto;
import com.dendeberia.facebookassignment.service.AdvertisementService;
import com.dendeberia.facebookassignment.service.FileService;
import com.facebook.ads.sdk.APIContext;
import com.facebook.ads.sdk.APIException;
import com.facebook.ads.sdk.Ad;
import com.facebook.ads.sdk.AdAccount;
import com.facebook.ads.sdk.AdCreativeLinkData;
import com.facebook.ads.sdk.AdCreativeObjectStorySpec;
import com.facebook.ads.sdk.AdImage;
import com.facebook.ads.sdk.AdSet;
import com.facebook.ads.sdk.Campaign;
import com.facebook.ads.sdk.Targeting;
import com.facebook.ads.sdk.TargetingGeoLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class AdvertisementServiceImpl implements AdvertisementService {
    private final AdAccount adAccount;
    private final FileService fileService;

    public AdvertisementServiceImpl(APIContext apiContext,
                                    @Value("${facebook.accounts.id}") String accountId,
                                    FileService fileService) {
        this.fileService = fileService;
        this.adAccount = new AdAccount(accountId, apiContext);
    }

    @Override
    public Ad createAd(CreateAdvertisementDto dto, MultipartFile imageFile) {
        try {
            AdImage image = null;

            image = adAccount.createAdImage()
                    .addUploadFile("file", fileService.toFile(imageFile))
                    .execute();

            Campaign campaign = createOrGetCampaign(dto);
            Targeting targeting = createTargeting(dto);
            AdSet adSet = createOrGetAdSet(campaign, dto, targeting);
            return createAd(adSet, dto, image);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private Campaign createOrGetCampaign(CreateAdvertisementDto dto) throws APIException {
        return adAccount.getCampaigns().execute().stream()
                .filter(campaign -> campaign.getFieldName().equals(dto.name()))
                .findFirst()
                .orElse(adAccount.createCampaign()
                        .setName(dto.name())
                        .setObjective(Campaign.EnumObjective.VALUE_LINK_CLICKS)
                        .setStatus(Campaign.EnumStatus.VALUE_PAUSED)
                        .execute());
    }

    private AdSet createOrGetAdSet(Campaign campaign, CreateAdvertisementDto dto, Targeting targeting) throws APIException {
        return adAccount.getAdSets().execute().stream()
                .filter(adSet -> adSet.getFieldName().equals(dto.name()))
                .findFirst()
                .orElse(adAccount.createAdSet()
                        .setName(dto.name())
                        .setCampaignId(campaign.getId())
                        .setStatus(AdSet.EnumStatus.VALUE_PAUSED)
                        .setBillingEvent(dto.enumBillingEvent())
                        .setDailyBudget(dto.dailyBudget())
                        .setTargeting(targeting)
                        .execute());
    }

    private Ad createAd(AdSet adSet, CreateAdvertisementDto dto, AdImage image) throws APIException {
        return adAccount.createAd()
                .setName(dto.name())
                .setAdsetId(adSet.getId())
                .setCreative(
                        adAccount.createAdCreative()
                                .setName(dto.name())
                                .setTitle(dto.title())
                                .setBody(dto.body())
                                .setObjectStorySpec(
                                        new AdCreativeObjectStorySpec()
                                                .setFieldLinkData(
                                                        new AdCreativeLinkData()
                                                                .setFieldLink(dto.link())
                                                                .setFieldImageHash(image.getFieldHash())
                                                )
                                )
                                .execute()
                )
                .setStatus(Ad.EnumStatus.VALUE_PAUSED)
                .execute();
    }

    private Targeting createTargeting(CreateAdvertisementDto dto) {
        return new Targeting()
                .setFieldGeoLocations(new TargetingGeoLocation().setFieldCountries(dto.targetingCountries()))
                .setFieldAgeMin(dto.targetingAgeMin())
                .setFieldAgeMax(dto.targetingAgeMax());
    }
}
