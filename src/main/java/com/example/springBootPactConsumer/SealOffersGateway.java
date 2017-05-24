package com.example.springBootPactConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SealOffersGateway {

    private final RestTemplate sealRestTemplate;
    @Value("${protocolAndDomain}")
    private String protocolAndDomain;

    public SealOffersGateway(RestTemplate sealRestTemplate) {
        this.sealRestTemplate = sealRestTemplate;
    }

    public List<TravelOfferEntity> fetchOffers() {
        String offersUrl = protocolAndDomain + "/api/v1/offers";
        ResponseEntity<List<TravelOfferEntity>> offersResponse = sealRestTemplate.exchange(
                offersUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TravelOfferEntity>>() {
                });
        List<TravelOfferEntity> offers = offersResponse.getBody();
        if (offers == null) {
            return new ArrayList<>();
        }
        return offers;
    }
}
