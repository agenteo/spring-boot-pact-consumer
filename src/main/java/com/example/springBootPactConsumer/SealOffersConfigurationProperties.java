package com.example.springBootPactConsumer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class SealOffersConfigurationProperties {
    public String getProtocolAndDomain() {
        return "http://localhost:9292";
    }

    public String getClientId() {
        return "a-client-id";
    }

    public String getClientSecret() {
        return "a-very-big-secret";
    }
}
