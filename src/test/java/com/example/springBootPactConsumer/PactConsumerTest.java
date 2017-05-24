package com.example.springBootPactConsumer;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.*;
import au.com.dius.pact.model.PactFragment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
public class PactConsumerTest {

    @Autowired
    private SealOffersGateway subject;

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("seal_API", "localhost", 9292, this);

    @Pact(provider = "seal_API", consumer = "aggregator_app")
    public PactFragment createFragment(PactDslWithProvider builder) throws IOException {
        HashMap<String, String> applicationJsonContent = new HashMap<>();

        DslPart oauthTokenBody = new PactDslJsonBody()
                .stringMatcher("access_token", "\\w*", "e72e16c7e42f292c6912e7710c838347ae178b4a")
                .stringValue("token_type", "Bearer");
        applicationJsonContent.put("Content-Type", "application/json; charset=utf-8");



        DslPart baseOffersBody = PactDslJsonArray
                .arrayEachLike()
                .stringMatcher("uuid", "(\\w|-)*", "c5195583-ff66-454f-babb-77d8d8b6e463")
                .stringMatcher("destination_name", ".*", "Mantova")
                .stringMatcher("operator_name", ".*", "Ocean")
                .stringMatcher("status", "NEW|OK|ENDING", "OK")
                .stringMatcher("offer_start_date", ".*", "2012-03-02T08:00:00.000Z")
                .decimalType("price", 2000.22)
                .stringMatcher("offer_end_date", ".*", "2014-07-01T03:59:59.999Z")
                .closeObject();

        return oauthResponse(builder)
                .headers(applicationJsonContent)
                .body(oauthTokenBody)
                .given("There are available travel offers")
                .uponReceiving("A request for all offers")
                .path("/api/v1/offers")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(applicationJsonContent)
                .body(baseOffersBody)
                .toFragment();
    }

    private PactDslResponse oauthResponse(PactDslWithProvider builder) {
        return builder
                .given("OAuth endpoints")
                .uponReceiving("OAuth endpoint returns ok when given correct input")
                .path("/oauth/token")
                .headers("Content-Type", APPLICATION_FORM_URLENCODED_VALUE)
                .matchHeader("Authorization", "Basic .*", "Basic base64 encoded clientid+clientsecret")
                .query("grant_type=client_credentials")
                .method("POST")
                .willRespondWith()
                .status(200);
    }

    @Test
    @PactVerification("seal_API")
    public void runOffersTest() throws IOException {
        List<TravelOfferEntity> travelOfferEntities = subject.fetchOffers();

        assertThat(travelOfferEntities, hasSize(1));
        assertThat(travelOfferEntities.get(0).getUuid(), not(nullValue()));
        assertThat(travelOfferEntities.get(0).getDestinationName(), not(nullValue()));
        assertThat(travelOfferEntities.get(0).getOperatorName(), not(nullValue()));
        assertThat(travelOfferEntities.get(0).getStatus(), not(nullValue()));
        assertThat(travelOfferEntities.get(0).getOfferStartDate(), not(nullValue()));
        assertThat(travelOfferEntities.get(0).getOfferEndDate(), not(nullValue()));
        assertThat(travelOfferEntities.get(0).getPrice(), not(nullValue()));
    }

}