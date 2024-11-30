package com.tomaston.etastocks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AVEtfProfileSectorsData implements Serializable {
    @JsonProperty("sector")
    public String sector;

    @JsonProperty("weight")
    public String weight;
}
