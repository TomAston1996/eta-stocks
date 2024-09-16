package com.tomaston.etastocks.service.impl;

import com.tomaston.etastocks.domain.*;
import com.tomaston.etastocks.service.AlphaVantageStocksClient;
import com.tomaston.etastocks.service.AlphaVantageStocksService;
import com.tomaston.etastocks.utils.DateTimeConverter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AlphaVantageStocksServiceImpl implements AlphaVantageStocksService {

    private static Logger log = LoggerFactory.getLogger(AlphaVantageStocksClientImpl.class);

    private final AlphaVantageStocksClient avStocksClient;

    public AlphaVantageStocksServiceImpl(final AlphaVantageStocksClient avStocksClient) {
        this.avStocksClient = avStocksClient;
    }

    public AVTimeSeriesClientData getTimeSeriesMonthlyStockData(final String symbol) {
        final AVTimeSeriesMonthlyJson response = avStocksClient.getAlphaVantageTimeSeriesMonthlyStock(symbol);

        log.info(String.valueOf(response.toString()));

        //create a AVTimeSeriesClientData object to return to the client in JSON format
        AVTimeSeriesClientData clientResponse = new AVTimeSeriesClientData();

        //create list of monthly stock data
        List<AVTimeSeriesJsonCleanData> timeSeriesStockData = new ArrayList<>();
        for (Map.Entry<String, AVTimeSeriesJsonRawData> entry : response.monthly.entrySet()) {
            Long unixDateTime = DateTimeConverter.stringToUnix(entry.getKey());
            AVTimeSeriesJsonRawData data = entry.getValue();
            AVTimeSeriesJsonCleanData obj = new AVTimeSeriesJsonCleanData(
                    unixDateTime,
                    Double.parseDouble(data.openingPrice),
                    Double.parseDouble(data.closingPrice),
                    Double.parseDouble(data.lowPrice),
                    Double.parseDouble(data.highPrice)
            );

            timeSeriesStockData.add(obj);
        }

        //build the response with clean formatting
        clientResponse.metaData = response.metaData;
        clientResponse.seriesData = timeSeriesStockData;

        return clientResponse;
    }
}
