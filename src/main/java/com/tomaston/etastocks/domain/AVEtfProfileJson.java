package com.tomaston.etastocks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AVEtfProfileJson {
    @JsonProperty("sectors")
    public List<AVEtfProfileSectorsData> sectorsData;

    @JsonProperty("holdings")
    public List<AVEtfProfileHoldingsData> holdingsData;

    @JsonProperty("Information")
    public String errorInfo;
}
