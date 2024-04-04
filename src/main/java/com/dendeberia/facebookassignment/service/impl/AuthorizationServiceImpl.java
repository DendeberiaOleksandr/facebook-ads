package com.dendeberia.facebookassignment.service.impl;

import com.dendeberia.facebookassignment.config.FacebookCredentials;
import com.dendeberia.facebookassignment.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final String ACCESS_TOKEN_URL = "https://graph.facebook.com/oauth/access_token?client_id={clientId}&client_secret={clientSecret}&grant_type=client_credentials";

    private final RestTemplate restTemplate;
    private final FacebookCredentials facebookCredentials;

    @Override
    public String exchangeToken() {
        return (String) restTemplate.getForObject(ACCESS_TOKEN_URL, Map.class, facebookCredentials.getId(),
                facebookCredentials.getSecret())
                .get("access_token");
    }
}
