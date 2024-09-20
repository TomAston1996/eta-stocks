package com.tomaston.etastocks.service;

import com.tomaston.etastocks.domain.AVTimeSeriesJson;
import com.tomaston.etastocks.domain.AVTimeSeriesJsonCleanData;
import com.tomaston.etastocks.domain.AVTimeSeriesJsonMetaData;
import com.tomaston.etastocks.dto.AVTimeSeriesDTO;
import com.tomaston.etastocks.utils.FileUtil;
import com.tomaston.etastocks.utils.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AlphaVantageStocksServiceTest {

    @Mock
    AlphaVantageStocksClient alphaVantageStocksClient;

    @InjectMocks
    AlphaVantageStocksService alphaVantageStocksService;

    AVTimeSeriesJson avRawDataTestJson;

    @BeforeEach
    void setUp() throws IOException {
        String avRawDataJson = FileUtil.readFromFileToString("/files/alpha_vantage_raw_stock_data.json");
        this.avRawDataTestJson = MapperUtil.deserializeAVRawSeries(avRawDataJson);

        AVTimeSeriesDTO res = this.alphaVantageStocksService.convertAlphaVantageRawResponse(
                this.avRawDataTestJson.metaData,
                this.avRawDataTestJson.seriesData
        );
    }

    /** Unit test for cleaning function for time series data returned from the AV client
     */
    @Test
    void shouldReturnTimeSeriesDTO() {
        AVTimeSeriesDTO res = this.alphaVantageStocksService.convertAlphaVantageRawResponse(
                this.avRawDataTestJson.metaData,
                this.avRawDataTestJson.seriesData
        );

        List<AVTimeSeriesJsonCleanData> seriesData = res.seriesData;

        //there are 6x series data objects in the tes data
        assertEquals(seriesData.size(), 6);

        //make sure each data object contains the correct info
        for (AVTimeSeriesJsonCleanData data : seriesData) {
            assertAll("Series Data Null Check",
                    () -> assertNotNull(data.closeStockPrice()),
                    () -> assertNotNull(data.lowStockPrice()),
                    () -> assertNotNull(data.highStockPrice()),
                    () -> assertNotNull(data.openStockPrice()),
                    () -> assertEquals(data.unixTimestamp().getClass(), Long.class)
            );
        }
    }

    /** Unit test for cleaning function for metadata returned from the AV client
     */
    @Test
    void shouldReturnMetaData() {
        AVTimeSeriesDTO res = this.alphaVantageStocksService.convertAlphaVantageRawResponse(
                this.avRawDataTestJson.metaData,
                this.avRawDataTestJson.seriesData
        );

        AVTimeSeriesJsonMetaData metaData = res.metaData;

        //make sure all metadata values are present in the returned object
        assertAll("Meta Data Null Check",
                () -> assertNotNull(metaData.info),
                () -> assertNotNull(metaData.lastRefreshed),
                () -> assertNotNull(metaData.symbol),
                () -> assertNotNull(metaData.timeZone)
        );
    }
}