package com.tomaston.etastocks.service;

import com.tomaston.etastocks.domain.AVTimeSeriesClientData;

/**
 * Interface for AV Stocks external API service
 */
public interface AlphaVantageStocksService {
    AVTimeSeriesClientData getTimeSeriesMonthlyStockData(final String symbol);
}
