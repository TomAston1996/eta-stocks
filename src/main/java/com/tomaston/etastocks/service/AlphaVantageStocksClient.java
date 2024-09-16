package com.tomaston.etastocks.service;

import com.tomaston.etastocks.domain.AVTimeSeriesMonthlyJson;

/**
 * Interface for AV Stocks external API client
 */
public interface AlphaVantageStocksClient {
    AVTimeSeriesMonthlyJson getAlphaVantageTimeSeriesMonthlyStock(final String symbol);
}
