package com.tomaston.etastocks.service;

import com.tomaston.etastocks.dto.AVTimeSeriesDTO;

/**
 * Interface for AV Stocks external API service
 */
public interface AlphaVantageStocksService {
    AVTimeSeriesDTO getTimeSeriesMonthlyStockData(final String symbol);

    AVTimeSeriesDTO getTimeSeriesDailyStockData(final String symbol);
}
