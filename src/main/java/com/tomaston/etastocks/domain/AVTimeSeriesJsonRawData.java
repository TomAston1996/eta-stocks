package com.tomaston.etastocks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Models the raw JSON series data from AlphaVantageStocksClientImpl
 * Example Data:
 * "2024-09-13": {
 *      "1. open": "81.2800",
 *      "2. high": "83.0000",
 *      "3. low": "78.1200",
 *      "4. close": "81.1800",
 *      "5. volume": "1679239"
 * },
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AVTimeSeriesJsonRawData {
    @JsonProperty("1. open")
    public String openingPrice;
    @JsonProperty("2. high")
    public String highPrice;
    @JsonProperty("3. low")
    public String lowPrice;
    @JsonProperty("4. close")
    public String closingPrice;
    @JsonProperty("5. volume")
    public String volume;
}
