package com.tomaston.etastocks.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AVTickerSearchDTO implements Serializable {
    public List<AVTickerDataDTO> bestMatches;
}
