package com.dendeberia.facebookassignment.service;

import com.dendeberia.facebookassignment.dto.CreateAdvertisementDto;
import com.facebook.ads.sdk.Ad;
import org.springframework.web.multipart.MultipartFile;

public interface AdvertisementService {

    Ad createAd(CreateAdvertisementDto dto, MultipartFile image);

}
