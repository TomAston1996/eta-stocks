package com.tomaston.etastocks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AVEtfProfileHoldingsData {
    @JsonProperty("symbol")
    public String symbol;

    @JsonProperty("description")
    public String description;

    @JsonProperty("weight")
    public String weight;
}
