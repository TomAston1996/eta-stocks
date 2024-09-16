package com.tomaston.etastocks.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AVTimeSeriesClientData {
    public AVTimeSeriesJsonMetaData metaData;
    public List<AVTimeSeriesJsonCleanData> seriesData;
}
