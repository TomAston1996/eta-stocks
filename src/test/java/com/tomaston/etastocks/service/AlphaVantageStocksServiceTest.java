package com.tomaston.etastocks.service;

import com.tomaston.etastocks.domain.AVTimeSeriesJson;
import com.tomaston.etastocks.dto.AVTimeSeriesDTO;
import com.tomaston.etastocks.utils.FileUtil;
import com.tomaston.etastocks.utils.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AlphaVantageStocksServiceTest {

    AlphaVantageStocksService alphaVantageStocksService;

    AVTimeSeriesJson avRawDataTestJson;

    @BeforeEach
    void setUp() throws IOException {
        this.alphaVantageStocksService = new AlphaVantageStocksService(
                new AlphaVantageStocksClient(
                        new RestTemplate()
                )
        );
        String avRawDataJson = FileUtil.readFromFileToString("/files/alpha_vantage_raw_stock_data.json");
        this.avRawDataTestJson = MapperUtil.deserializeAVRawSeries(avRawDataJson);

        AVTimeSeriesDTO res = this.alphaVantageStocksService.convertAlphaVantageRawResponse(
                this.avRawDataTestJson.metaData,
                this.avRawDataTestJson.seriesData
        );
    }

    @Test
    void shouldReturnTrue() {
        alphaVantageStocksService.convertAlphaVantageRawResponse(avRawDataTestJson.metaData, avRawDataTestJson.seriesData);
        assertTrue(true);
    }

    @Test
    void shouldReturnTimeSeriesDTO() {
        AVTimeSeriesDTO res = this.alphaVantageStocksService.convertAlphaVantageRawResponse(
                this.avRawDataTestJson.metaData,
                this.avRawDataTestJson.seriesData
        );

        //make sure we get a response back
        assertNotEquals(res, null);

        //there are 6x series data objects in the tes data
        assertEquals(res.seriesData.size(), 6);
    }
}