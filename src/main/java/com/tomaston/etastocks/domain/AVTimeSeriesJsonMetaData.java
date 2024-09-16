package com.tomaston.etastocks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Models the meta JSON data from AlphaVantageStocksClientImpl
 * Example Data:
 *"Metadata": {
 *    "1. Information": "Monthly Prices (open, high, low, close) and Volumes",
 *    "2. Symbol": "LON:VUAG",
 *    "3. Last Refreshed": "2024-09-13",
 *    "4. Time Zone": "US/Eastern"
 * }
 * */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AVTimeSeriesJsonMetaData {
    @JsonProperty("1. Information")
    public String info;
    @JsonProperty("2. Symbol")
    public String symbol;
    @JsonProperty("3. Last Refreshed")
    public String lastRefreshed;
    @JsonProperty("4. Time Zone")
    public String timeZone;
}
