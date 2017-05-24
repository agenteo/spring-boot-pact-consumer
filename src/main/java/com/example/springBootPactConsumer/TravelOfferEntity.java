package com.example.springBootPactConsumer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TravelOfferEntity {

    private String uuid;
    @JsonProperty("destination_name")
    private String destinationName;
    @JsonProperty("operator_name")
    private String operatorName;
    private String status;
    @JsonProperty("offer_start_date")
    private String offerStartDate;
    @JsonProperty("offer_end_date")
    private String offerEndDate;
    private String price;

    public String getUuid() {
        return uuid;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public String getStatus() {
        return status;
    }

    public String getOfferStartDate() {
        return offerStartDate;
    }

    public String getOfferEndDate() {
        return offerEndDate;
    }

    public String getPrice() {
        return price;
    }
}
