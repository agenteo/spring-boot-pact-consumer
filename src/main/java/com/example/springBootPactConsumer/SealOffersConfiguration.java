package com.example.springBootPactConsumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SealOffersConfiguration {

    @Bean
    public RestTemplate sealRestTemplate(SealOffersConfigurationProperties sealOffersConfigurationProperties) {
        String tokenUrl = sealOffersConfigurationProperties.getProtocolAndDomain() + "/oauth/token";
        ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
        resource.setClientId(sealOffersConfigurationProperties.getClientId());
        resource.setClientSecret(sealOffersConfigurationProperties.getClientSecret());
        resource.setAccessTokenUri(tokenUrl);

        return new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext());
    }

}
