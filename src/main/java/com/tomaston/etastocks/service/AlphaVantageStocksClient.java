package com.tomaston.etastocks.service;

import com.tomaston.etastocks.domain.AVTimeSeriesJson;
import com.tomaston.etastocks.exception.ApiRequestException;
import com.tomaston.etastocks.exception.RateLimitedRequestException;
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
public class AlphaVantageStocksClient {

    private static Logger log = LoggerFactory.getLogger(AlphaVantageStocksClient.class);

    private final RestTemplate restTemplate;
    private final String BASE_URL = "https://www.alphavantage.co/";

    @Value("${alphaVantageApiKey}")
    private String alphaVantageApiKey;

    private final Map<String, String> functionMap;

    public AlphaVantageStocksClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.functionMap = Map.of(
                "monthly", "TIME_SERIES_MONTHLY",
                "daily", "TIME_SERIES_DAILY"
        );
    }

    /**
     * Get time series stock data from the alpha vantage free stocks api https://www.alphavantage.co/
     * @param symbol ticker code i.e LON:VOO
     * @param function period for series data i.e monthly or daily
     * @return AVTimeSeries Json object
     */
    public AVTimeSeriesJson getAlphaVantageTimeSeriesStockData(final String symbol, final String function) {
        String functionCode;
        if (functionMap.containsKey(function)) {
            functionCode = functionMap.get(function);
        } else {
            log.debug("Function code: '{}' used", function);
            throw new ApiRequestException("Information: the 'function' request parameter you provided to Alpha Vantage is not valid." +
                    " Function options are: {'monthly', 'daily'}");
        }

        final ParameterizedTypeReference<AVTimeSeriesJson> avTimeSeriesStockResponse = new ParameterizedTypeReference<>() {
        };

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("function", functionCode);
        params.put("symbol", symbol);
        params.put("apikey", alphaVantageApiKey);

        try {
            final ResponseEntity<AVTimeSeriesJson> response = restTemplate.exchange(
                    BASE_URL + "query?function={function}&symbol={symbol}&apikey={apikey}",
                    HttpMethod.GET,
                    entity,
                    avTimeSeriesStockResponse,
                    params
            );

            if (response.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                throw new RateLimitedRequestException("Information: Alpha Vantage limits API calls to 25 requests per" +
                        " day for free users.");
            }

            //Alpha Vantage API doesn't send a 4XX responses when request parameters are incorrect, therefore if
            //the contents of the body is just a string (no metaData or seriesData) it is an error message
            if (Objects.requireNonNull(response.getBody()).seriesData == null) {
                log.debug(response.getBody().toString());
                throw new ApiRequestException("Information: the request params to Alpha Vantage are likely not valid.");
            }

            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }
}
