package com.tomaston.etastocks.dto;

import com.tomaston.etastocks.domain.AVTimeSeriesJsonCleanData;
import com.tomaston.etastocks.domain.AVTimeSeriesJsonMetaData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AVTimeSeriesDTO {
    public AVTimeSeriesJsonMetaData metaData;
    public List<AVTimeSeriesJsonCleanData> seriesData;
}