package com.tomaston.etastocks.domain;

/** Models the cleaned JSON series data from AlphaVantageStocksServiceImpl
 * @param unixTimestamp
 * @param openStockPrice
 * @param closeStockPrice
 * @param lowStockPrice
 * @param highStockPrice
 */
public record AVTimeSeriesJsonCleanData(
        Long unixTimestamp,
        Double openStockPrice,
        Double closeStockPrice,
        Double lowStockPrice,
        Double highStockPrice
) {}
