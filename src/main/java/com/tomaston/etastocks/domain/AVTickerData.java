package com.tomaston.etastocks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AVTickerData {
    @JsonProperty("1. symbol")
    public String symbol;

    @JsonProperty("2. name")
    public String name;

    @JsonProperty("3. type")
    public String type;

    @JsonProperty("4. region")
    public String region;

    @JsonProperty("8. currency")
    public String currency;
}
