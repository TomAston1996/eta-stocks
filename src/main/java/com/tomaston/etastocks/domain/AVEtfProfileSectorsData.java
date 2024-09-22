package com.tomaston.etastocks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AVEtfProfileSectorsData {
    @JsonProperty("sector")
    public String sector;

    @JsonProperty("weight")
    public String weight;
}
