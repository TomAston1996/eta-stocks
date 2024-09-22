package com.tomaston.etastocks.controller;

import com.tomaston.etastocks.dto.AVEtfProfileDTO;
import com.tomaston.etastocks.dto.AVTimeSeriesDTO;
import com.tomaston.etastocks.service.AlphaVantageStocksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
public class StocksController {

    private final AlphaVantageStocksService stocksService;

    public StocksController(final AlphaVantageStocksService stocksService) {
        this.stocksService = stocksService;
    }

    /** Gets monthly data from Alpha Vantage stocks API service
     * @param symbol ticker code for the stock or ETF
     * @param function options = 'monthly' or 'daily'
     * @return JSON response
     */
    @GetMapping(value="/series", produces="application/json")
    public ResponseEntity<AVTimeSeriesDTO> getTimeSeriesStockData(
            @RequestParam(name="symbol", required=true) final String symbol,
            @RequestParam(name="function", required=true) final String function
    ) {
        return new ResponseEntity<AVTimeSeriesDTO>(stocksService.getTimeSeriesStockData(symbol, function), HttpStatus.OK);
    }

    @GetMapping(value="/etf", produces="application/json")
    public ResponseEntity<AVEtfProfileDTO> getEtfProfileData(
            @RequestParam(name="symbol", required=true) final String symbol,
            @RequestParam(name="function", required=true) final String function
    ) {
        return new ResponseEntity<AVEtfProfileDTO>(stocksService.getEtfProfileData(symbol, function), HttpStatus.OK);
    }

}
