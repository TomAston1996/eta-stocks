package com.tomaston.etastocks.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomaston.etastocks.domain.AVTimeSeriesJson;
import com.tomaston.etastocks.domain.AVTimeSeriesJsonMetaData;
import com.tomaston.etastocks.domain.AVTimeSeriesJsonRawData;

import java.util.Map;

public class MapperUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static AVTimeSeriesJson deserializeAVRawSeries(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, AVTimeSeriesJson.class);
    }

    public static String stringifyAVRawSeries(AVTimeSeriesJson obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
