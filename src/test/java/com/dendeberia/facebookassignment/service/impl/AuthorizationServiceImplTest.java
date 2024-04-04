package com.dendeberia.facebookassignment.service.impl;

import com.dendeberia.facebookassignment.config.FacebookCredentials;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceImplTest {

    AuthorizationServiceImpl authorizationService;

    @Test
    void exchangeToken_whenValidClientCredentialsProvided_thenShouldExchangeToken() {
        // given
        authorizationService = new AuthorizationServiceImpl(new RestTemplate(),
                new FacebookCredentials("1810392512812156", "de5e6467a3c72f73f14aa0428578baf8", null));

        // when
        String token = authorizationService.exchangeToken();

        // then
        assertTrue(StringUtils.hasText(token));
    }

    @Test
    void exchangeToken_whenInvalidClientCredentialsProvided_thenShouldNotExchangeToken() {
        // given
        authorizationService = new AuthorizationServiceImpl(new RestTemplate(),
                new FacebookCredentials("invalid", "invalid", null));

        // when
        assertThrows(HttpClientErrorException.class, () -> authorizationService.exchangeToken());

        // then
    }

}