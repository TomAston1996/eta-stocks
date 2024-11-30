package com.tomaston.etastocks.controller;

import com.tomaston.etastocks.dto.AVEtfProfileDTO;
import com.tomaston.etastocks.dto.AVTickerSearchDTO;
import com.tomaston.etastocks.dto.AVTimeSeriesDTO;
import com.tomaston.etastocks.service.AlphaVantageStocksClient;
import com.tomaston.etastocks.service.AlphaVantageStocksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/avStocks")
public class AlphaVantageController {

    private static final Logger log = LoggerFactory.getLogger(AlphaVantageController.class);

    private final AlphaVantageStocksService stocksService;

    public AlphaVantageController(final AlphaVantageStocksService stocksService) {
        this.stocksService = stocksService;
    }

    /** Gets monthly data from Alpha Vantage stocks API service
     * @param symbol ticker code for the stock or ETF
     * @param function options = 'monthly' or 'daily'
     * @return JSON response
     */
    @GetMapping(value="/series", produces="application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<AVTimeSeriesDTO> getTimeSeriesStockData(
            @RequestParam(name="symbol", required=true) final String symbol,
            @RequestParam(name="function", required=true) final String function
    ) {
        log.info("AV controller time series request...");
        return new ResponseEntity<AVTimeSeriesDTO>(stocksService.getTimeSeriesStockData(symbol, function), HttpStatus.OK);
    }

    /**
     * @param symbol ticker code for the stock or ETF
     * @param function options = 'etfProfile' //TODO to be removed
     * @return JSON
     */
    @GetMapping(value="/etf", produces="application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<AVEtfProfileDTO> getEtfProfileData(
            @RequestParam(name="symbol", required=true) final String symbol,
            @RequestParam(name="function", required=true) final String function
    ) {
        return new ResponseEntity<AVEtfProfileDTO>(stocksService.getEtfProfileData(symbol, function), HttpStatus.OK);
    }

    /**
     * @param symbol ticker code for the stock or ETF
     * @return JSON
     */
    @GetMapping(value="/searchTicker", produces="application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<AVTickerSearchDTO> getTickerSearchData(
            @RequestParam(name="symbol", required=true) final String symbol
    ) {
        return new ResponseEntity<AVTickerSearchDTO>(stocksService.getTickerSearchData(symbol), HttpStatus.OK);
    }

}
