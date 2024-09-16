package com.tomaston.etastocks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AVTimeSeriesDailyJson {
    @JsonProperty("Meta Data")
    public AVTimeSeriesJsonMetaData metaData;

    @JsonProperty("Time Series (Daily)")
    public Map<String, AVTimeSeriesJsonRawData> seriesData;
}
