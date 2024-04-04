package com.dendeberia.facebookassignment.config;

import com.dendeberia.facebookassignment.service.AuthorizationService;
import com.facebook.ads.sdk.APIContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class FacebookConfig {

    @Bean
    public APIContext apiContext(AuthorizationService authorizationService, FacebookCredentials facebookCredentials) {
        String accessToken = facebookCredentials.getAccessToken();
        APIContext apiContext = new APIContext(StringUtils.hasText(accessToken) ? accessToken : authorizationService.exchangeToken(),
                facebookCredentials.getSecret());
        apiContext.enableDebug(true);
        return apiContext;
    }

}
