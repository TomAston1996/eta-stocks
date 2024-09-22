package com.tomaston.etastocks.controller;

import com.tomaston.etastocks.dto.AVTimeSeriesDTO;
import com.tomaston.etastocks.service.AlphaVantageStocksService;
import com.tomaston.etastocks.utils.FileUtil;
import com.tomaston.etastocks.utils.MapperUtil;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = StocksController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class StocksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlphaVantageStocksService stocksService;

    AVTimeSeriesDTO avClientDataTestJson;

    @BeforeEach
    void setUp() throws IOException {
        String avClientDataJson = FileUtil.readFromFileToString("/files/alpha_vantage_client_stock_data.json");
        this.avClientDataTestJson = MapperUtil.deserializeAVClientSeries(avClientDataJson);
    }

    /** Tests GET method for obtaining AV time series stock data
     */
    @Test
    public void shouldReturnAVSeriesClientData() throws Exception {

        when(stocksService.getTimeSeriesStockData("LON:VUAG", "monthly")).thenReturn(avClientDataTestJson);

        ResultActions response = mockMvc.perform(get("/api/stocks/series")
                .contentType(MediaType.APPLICATION_JSON)
                .param("symbol", "LON:VUAG")
                .param("function", "monthly"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.seriesData.size()", CoreMatchers.is(avClientDataTestJson.seriesData.size())));
    }

}