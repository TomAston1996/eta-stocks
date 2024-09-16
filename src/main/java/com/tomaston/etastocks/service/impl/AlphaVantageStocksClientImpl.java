package com.tomaston.etastocks.service.impl;

import com.tomaston.etastocks.domain.AVTimeSeriesMonthlyJson;
import com.tomaston.etastocks.service.AlphaVantageStocksClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
public class AlphaVantageStocksClientImpl implements AlphaVantageStocksClient {

    private static Logger log = LoggerFactory.getLogger(AlphaVantageStocksClientImpl.class);

    private final RestTemplate restTemplate;
    private final String BASE_URL = "https://www.alphavantage.co/";

    @Value("${alphaVantageApiKey}")
    private String alphaVantageApiKey;

    public AlphaVantageStocksClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public AVTimeSeriesMonthlyJson getAlphaVantageTimeSeriesMonthlyStock(final String symbol) {
        final ParameterizedTypeReference<AVTimeSeriesMonthlyJson> avTimeSeriesStockResponse = new ParameterizedTypeReference<>() {
        };

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("function", "TIME_SERIES_MONTHLY");
        params.put("symbol", symbol);
        params.put("apikey", alphaVantageApiKey);

        try {
            final ResponseEntity<AVTimeSeriesMonthlyJson> response = restTemplate.exchange(
                    BASE_URL + "query?function={function}&symbol={symbol}&apikey={apikey}",
                    HttpMethod.GET,
                    entity,
                    avTimeSeriesStockResponse,
                    params
            );

            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }
}
