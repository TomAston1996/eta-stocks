package com.tomaston.etastocks.controller;

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
     * @return JSON response
     */
    @GetMapping(value="/monthly", produces="application/json")
    public ResponseEntity<AVTimeSeriesDTO> getTimeSeriesStockMonthlyData(
            @RequestParam(name="symbol", required=true) final String symbol
    ) {
        return new ResponseEntity<AVTimeSeriesDTO>(stocksService.getTimeSeriesMonthlyStockData(symbol), HttpStatus.OK);
    }

    /** Gets daily data from Alpha Vantage stocks API service
     * @param symbol ticker code for the stock or ETF
     * @return JSON response
     */
    @GetMapping(value="/daily", produces="application/json")
    public ResponseEntity<AVTimeSeriesDTO> getTimeSeriesStockDailyData(
            @RequestParam(name="symbol", required=true) final String symbol
    ) {
        return new ResponseEntity<AVTimeSeriesDTO>(stocksService.getTimeSeriesDailyStockData(symbol), HttpStatus.OK);
    }

}
