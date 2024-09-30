package com.tomaston.etastocks.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AVTickerSearchDTO {
    public List<AVTickerDataDTO> bestMatches;
}
