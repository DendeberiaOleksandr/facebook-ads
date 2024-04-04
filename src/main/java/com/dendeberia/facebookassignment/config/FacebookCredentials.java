package com.dendeberia.facebookassignment.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class FacebookCredentials {

    private final String id;
    private final String secret;
    private final String accessToken;

    public FacebookCredentials(@Value("${facebook.client.id}") String id,
                               @Value("${facebook.client.secret}") String secret,
                               @Value("${facebook.client.accessToken:}") String accessToken) {
        this.id = id;
        this.secret = secret;
        this.accessToken = accessToken;
    }
}
