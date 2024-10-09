package com.tomaston.etastocks.service;

import com.tomaston.etastocks.domain.AVEtfProfileJson;
import com.tomaston.etastocks.domain.AVTickerProfileJson;
import com.tomaston.etastocks.domain.AVTimeSeriesJson;
import com.tomaston.etastocks.exception.BadRequestException;
import com.tomaston.etastocks.exception.NotFoundRequestException;
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

    private static final Logger log = LoggerFactory.getLogger(AlphaVantageStocksClient.class);

    private final RestTemplate restTemplate;
    private final String BASE_URL = "https://www.alphavantage.co/";

    @Value("${alphaVantageApiKey}")
    private String alphaVantageApiKey;

    private final Map<String, String> functionMap;

    public AlphaVantageStocksClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.functionMap = Map.of(
                "monthly", "TIME_SERIES_MONTHLY",
                "daily", "TIME_SERIES_DAILY",
                "etfProfile", "ETF_PROFILE"
        );
    }

    /**
     * Get time series stock data from the alpha vantage free stocks api https://www.alphavantage.co/
     * @param symbol ticker code i.e LON:VOO
     * @param function period for series data i.e monthly or daily
     * @return AVTimeSeries Json object
     */
    public AVTimeSeriesJson getAlphaVantageTimeSeriesStockData(final String symbol, final String function) {
        String functionCode = getValidFunctionCode(function);

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

            //Alpha Vantage API doesn't send a 4XX responses when request parameters are incorrect, therefore if
            //the contents of the body is just a string (no metaData or seriesData) it is an error message
            if (Objects.requireNonNull(response.getBody()).seriesData == null) {
                String errorInfo = response.getBody().errorInfo;
                log.debug(errorInfo);
                if (errorInfo.contains("API rate limit")) {
                    throw new RateLimitedRequestException("Alpha Vantage limits API calls to 25 requests per" +
                            " day for free users.");
                } else {
                    throw new BadRequestException("The request params to Alpha Vantage are likely not valid.");
                }
            }

            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    AVEtfProfileJson getAlphaVantageEtfProfileData(final String symbol, final String function) {
        String functionCode = getValidFunctionCode(function);

        final ParameterizedTypeReference<AVEtfProfileJson> avTimeSeriesStockResponse = new ParameterizedTypeReference<>() {
        };

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("function", functionCode);
        params.put("symbol", symbol);
        params.put("apikey", alphaVantageApiKey);

        //TODO potential unnecessary code replication here - perhaps refactor
        try {
            final ResponseEntity<AVEtfProfileJson> response = restTemplate.exchange(
                    BASE_URL + "query?function={function}&symbol={symbol}&apikey={apikey}",
                    HttpMethod.GET,
                    entity,
                    avTimeSeriesStockResponse,
                    params
            );

            //Alpha Vantage API doesn't send a 4XX responses when request parameters are incorrect, therefore if
            //the contents of the body is just a string (sectors or holdings data) it is an error message
            if (Objects.requireNonNull(response.getBody()).sectorsData == null) {
                String errorInfo = response.getBody().errorInfo;
                log.debug(errorInfo);
                if (errorInfo.contains("API rate limit")) {
                    throw new RateLimitedRequestException("Alpha Vantage limits API calls to 25 requests per" +
                            " day for free users.");
                } else {
                    throw new BadRequestException("The request params to Alpha Vantage are likely not valid or the ETF does not" +
                            " exist on the Alpha Vantage ETF holdings database");
                }
            }

            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    AVTickerProfileJson getAlphaVantageTickerData(final String keywords) {

        final ParameterizedTypeReference<AVTickerProfileJson> avTickerSearchResponse = new ParameterizedTypeReference<>() {
        };

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("function", "SYMBOL_SEARCH");
        params.put("keywords", keywords);
        params.put("apikey", alphaVantageApiKey);

        //TODO potential unnecessary code replication here - perhaps refactor
        try {
            final ResponseEntity<AVTickerProfileJson> response = restTemplate.exchange(
                    BASE_URL + "query?function={function}&keywords={keywords}&apikey={apikey}",
                    HttpMethod.GET,
                    entity,
                    avTickerSearchResponse,
                    params
            );

            if (Objects.requireNonNull(response.getBody()).bestMatches.isEmpty()) {
                throw new NotFoundRequestException("No search results for this ETF symbol...");
            }

            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    private String getValidFunctionCode(String function) {
        if (functionMap.containsKey(function)) {
            return functionMap.get(function);
        } else {
            log.debug("Function code: '{}' used", function);
            throw new BadRequestException("The 'function' request parameter you provided to Alpha Vantage is not valid." +
                    " Function options are: {'monthly', 'daily'}");
        }
    }
}
