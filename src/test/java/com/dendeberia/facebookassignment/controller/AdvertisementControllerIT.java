package com.dendeberia.facebookassignment.controller;

import com.dendeberia.facebookassignment.dto.CreateAdvertisementDto;
import com.facebook.ads.sdk.AdSet;
import com.facebook.ads.sdk.Campaign;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.dendeberia.facebookassignment.util.ApiUtils.ADVERTISEMENTS_API;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdvertisementControllerIT {

    private static final String NAME = "Test";

    @Autowired
    MockMvc mvc;

    final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Test
    void createAdd_whenValidRequestProvided() {
        // given
        MockMultipartFile advertisement = new MockMultipartFile("advertisement", null, "application/json", objectMapper.writeValueAsBytes(new CreateAdvertisementDto(
                NAME, NAME, NAME, Campaign.EnumObjective.VALUE_EVENT_RESPONSES, AdSet.EnumBillingEvent.VALUE_APP_INSTALLS,
                1000L, 100000L, "http://example.com", List.of("US"),
                18L, 60L
        )));
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/png", getClass().getResourceAsStream("image.jpg"));

        // when
        mvc.perform(
                multipart(ADVERTISEMENTS_API)
                        .file(advertisement)
                        .file(image)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
        )
                .andExpect(status().isOk())
                .andDo(print());

        // then
    }

}