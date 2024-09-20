package com.tomaston.etastocks.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tomaston.etastocks.config.RestTemplateConfig;
import com.tomaston.etastocks.domain.AVTimeSeriesJson;
import com.tomaston.etastocks.exception.ApiRequestException;
import com.tomaston.etastocks.utils.FileUtil;
import com.tomaston.etastocks.utils.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ImportAutoConfiguration(classes = RestTemplateConfig.class)
@RestClientTest(AlphaVantageStocksClient.class)
class AlphaVantageStocksClientTest {

    @Autowired
    MockRestServiceServer server;

    @Autowired
    AlphaVantageStocksClient client;

    AVTimeSeriesJson avRawDataTestJson;

    @Value("${alphaVantageApiKey}")
    private String alphaVantageApiKey;

    @BeforeEach
    void setUp() throws IOException {
        server = MockRestServiceServer.createServer(new RestTemplate());
        String avRawDataJson = FileUtil.readFromFileToString("/files/alpha_vantage_raw_stock_data.json");
        this.avRawDataTestJson = MapperUtil.deserializeAVRawSeries(avRawDataJson);
    }

    @Test
    void shouldReturnTrue() throws JsonProcessingException {

        //when
        this.server.expect(requestTo(
                "https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=LON:VUAG&apikey=" + alphaVantageApiKey))
                .andRespond(withSuccess(MapperUtil.stringifyAVRawSeries(avRawDataTestJson), MediaType.APPLICATION_JSON));

        //then
        AVTimeSeriesJson response = client.getAlphaVantageTimeSeriesStockData("LON:VUAG", "monthly");
        assertEquals(response.seriesData.size(), avRawDataTestJson.seriesData.size());
    }

    @Test
    void shouldThrowBadRequestException() throws JsonProcessingException {
        assertThrows(
                ApiRequestException.class,
                () -> client.getAlphaVantageTimeSeriesStockData("LON:VUAG", "")
        );
    }

}