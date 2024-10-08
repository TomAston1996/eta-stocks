package com.tomaston.etastocks.service;

import com.tomaston.etastocks.domain.*;
import com.tomaston.etastocks.dto.AVEtfProfileDTO;
import com.tomaston.etastocks.dto.AVTickerDataDTO;
import com.tomaston.etastocks.dto.AVTickerSearchDTO;
import com.tomaston.etastocks.dto.AVTimeSeriesDTO;
import com.tomaston.etastocks.utils.DateTimeConverter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AlphaVantageStocksService {

    private static Logger log = LoggerFactory.getLogger(AlphaVantageStocksClient.class);

    private final AlphaVantageStocksClient avStocksClient;

    public AlphaVantageStocksService(final AlphaVantageStocksClient avStocksClient) {
        this.avStocksClient = avStocksClient;
    }

    /**
     * @param symbol stock or ETF ticker symbol
     * @param function options = 'monthly' or 'daily'
     * @return cleaned JSON data
     */
    public AVTimeSeriesDTO getTimeSeriesStockData(final String symbol, final String function) {
        final AVTimeSeriesJson response = avStocksClient.getAlphaVantageTimeSeriesStockData(symbol, function);
        return convertAlphaVantageRawResponse(response.metaData, response.seriesData);
    }

    /**
     * @param symbol stock or ETF ticker symbol
     * @param function options = 'etfProfile' //TODO remove this - is not needed from client
     * @return clean data
     */
    public AVEtfProfileDTO getEtfProfileData(final String symbol, final String function) {
        final AVEtfProfileJson response = avStocksClient.getAlphaVantageEtfProfileData(symbol, function);
        AVEtfProfileDTO avEtfProfileDTO = new AVEtfProfileDTO();
        avEtfProfileDTO.sectorsData = response.sectorsData;
        avEtfProfileDTO.topTenHoldings = response.holdingsData.stream().limit(10).collect(Collectors.toList());
        return avEtfProfileDTO;
    }

    /**
     * @param symbol symbol search string
     * @return list of tickers returned from search term
     */
    public AVTickerSearchDTO getTickerSearchData(final String symbol) {
        AVTickerProfileJson response = avStocksClient.getAlphaVantageTickerData(symbol);

        List<AVTickerDataDTO> bestMatches = new ArrayList<>(10);
        for (AVTickerData ticker : response.bestMatches) {
            AVTickerDataDTO clientTicker = new AVTickerDataDTO(
                    ticker.symbol,
                    ticker.name,
                    ticker.type,
                    ticker.region,
                    ticker.currency
            );
            bestMatches.add(clientTicker);
        }

        AVTickerSearchDTO dto = new AVTickerSearchDTO();
        dto.bestMatches = bestMatches;
        return dto;
    }

    /**
     * Convert raw AV response JSON to cleaned JSON for the eta-stocks application
     * @param metaData metadata
     * @param seriesData series stock data i.e. monthly, daily etc.
     * @return clean data
     */
    public AVTimeSeriesDTO convertAlphaVantageRawResponse(AVTimeSeriesJsonMetaData metaData, Map<String, AVTimeSeriesJsonRawData> seriesData) {
        //create a AVTimeSeriesClientData object to return to the client in JSON format
        AVTimeSeriesDTO clientResponse = new AVTimeSeriesDTO();

        //create list of monthly stock data
        List<AVTimeSeriesJsonCleanData> timeSeriesStockData = new ArrayList<>();
        for (Map.Entry<String, AVTimeSeriesJsonRawData> entry : seriesData.entrySet()) {
            Long unixDateTime = DateTimeConverter.stringToUnix(entry.getKey());
            AVTimeSeriesJsonRawData data = entry.getValue();
            AVTimeSeriesJsonCleanData obj = new AVTimeSeriesJsonCleanData(
                    unixDateTime,
                    Double.parseDouble(data.openingPrice),
                    Double.parseDouble(data.closingPrice),
                    Double.parseDouble(data.lowPrice),
                    Double.parseDouble(data.highPrice)
            );

            timeSeriesStockData.add(obj);
        }

        //build the response with clean formatting
        clientResponse.metaData = metaData;
        clientResponse.seriesData = timeSeriesStockData;

        return clientResponse;
    }
}
