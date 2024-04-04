package com.dendeberia.facebookassignment.controller;

import com.dendeberia.facebookassignment.dto.CreateAdvertisementDto;
import com.dendeberia.facebookassignment.service.AdvertisementService;
import com.facebook.ads.sdk.Ad;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.dendeberia.facebookassignment.util.ApiUtils.ADVERTISEMENTS_API;

@RestController
@RequestMapping(ADVERTISEMENTS_API)
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Ad createAdd(@RequestPart CreateAdvertisementDto advertisement,
                        @RequestPart MultipartFile image) {
        return advertisementService.createAd(advertisement, image);
    }

}
