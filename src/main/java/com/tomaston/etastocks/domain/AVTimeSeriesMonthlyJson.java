package com.tomaston.etastocks.domain;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AVTimeSeriesMonthlyJson {
    @JsonProperty("Meta Data")
    public AVTimeSeriesJsonMetaData metaData;

    @JsonProperty("Monthly Time Series")
    public Map<String, AVTimeSeriesJsonRawData> monthly;
}
