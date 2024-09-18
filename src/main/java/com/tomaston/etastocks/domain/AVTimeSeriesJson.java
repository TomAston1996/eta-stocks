package com.tomaston.etastocks.domain;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AVTimeSeriesJson {
    @JsonProperty("Meta Data")
    public AVTimeSeriesJsonMetaData metaData;

    @JsonAlias({"Monthly Time Series", "Time Series (Daily)"})
    public Map<String, AVTimeSeriesJsonRawData> seriesData;

    @JsonProperty("Information")
    public String errorInfo;


}
