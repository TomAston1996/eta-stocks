package com.tomaston.etastocks.dto;

import com.tomaston.etastocks.domain.AVEtfProfileHoldingsData;
import com.tomaston.etastocks.domain.AVEtfProfileSectorsData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AVEtfProfileDTO implements Serializable {
    public List<AVEtfProfileSectorsData> sectorsData;
    public List<AVEtfProfileHoldingsData> topTenHoldings;
}
