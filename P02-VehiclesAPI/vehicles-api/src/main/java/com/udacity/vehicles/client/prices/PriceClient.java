package com.udacity.vehicles.client.prices;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Implements a class to interface with the Pricing Client for price data.
 */
@Component
public class PriceClient {

    private static final Logger log = LoggerFactory.getLogger(PriceClient.class);

    private final RestTemplate restTemplate;

    private String pricingEndpoint;

    public PriceClient(RestTemplate restTemplate, @Value("${pricing.endpoint}") String pricingEndpoint) {
        this.restTemplate = restTemplate;
        this.pricingEndpoint = pricingEndpoint;
    }

    // In a real-world application we'll want to add some resilience
    // to this method with retries/CB/failover capabilities
    // We may also want to cache the results so we don't need to
    // do a request every time
    /**
     * Gets a vehicle price from the pricing client, given vehicle ID.
     * @param vehicleId ID number of the vehicle for which to get the price
     * @return Currency and price of the requested vehicle,
     *   error message that the vehicle ID is invalid, or note that the
     *   service is down.
     */
    public String getPrice(Long vehicleId) {
        try {
            URI uri =
                UriComponentsBuilder.fromUriString(pricingEndpoint)
                    .path("/services/price")
                    .queryParam("vehicleId", vehicleId)
                    .build()
                    .toUri();
            RequestEntity requestEntity = RequestEntity.get(uri).build();
            ParameterizedTypeReference<Price> ptr = new ParameterizedTypeReference<>() {};

            ResponseEntity<Price> priceResponse = restTemplate.exchange(requestEntity, ptr);
            if (priceResponse == null || !priceResponse.getStatusCode().equals(HttpStatus.OK)) {
                return "(consult price)";
            }
            return String.format("%s %s", priceResponse.getBody().getCurrency(),
                priceResponse.getBody().getPrice());

        } catch (Exception e) {
            log.error("Unexpected error retrieving price for vehicle {}", vehicleId, e);
        }
        return "(consult price)";
    }
}
