package com.tomaston.etastocks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AVTickerProfileJson {
    @JsonProperty("bestMatches")
    public List<AVTickerData> bestMatches;
}
