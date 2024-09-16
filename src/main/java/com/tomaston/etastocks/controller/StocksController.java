package com.tomaston.etastocks.controller;

import com.tomaston.etastocks.domain.AVTimeSeriesClientData;
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

    /**
     * @param function options = { TIME_SERIES_DAILY, TIME_SERIES_MONTHLY, etc. }
     * @param symbol ticker code for the stock or ETF
     * @return JSON response
     */
    @GetMapping(value="/monthly", produces="application/json")
    public ResponseEntity<AVTimeSeriesClientData> getTimeSeriesStockData(
            @RequestParam(name="symbol", required=true) final String symbol
    ) {
        return new ResponseEntity<AVTimeSeriesClientData>(stocksService.getTimeSeriesMonthlyStockData(symbol), HttpStatus.OK);
    }

}
