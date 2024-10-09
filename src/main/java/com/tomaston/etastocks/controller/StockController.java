package com.tomaston.etastocks.controller;

import com.tomaston.etastocks.domain.Stock;
import com.tomaston.etastocks.dto.StockDTO;
import com.tomaston.etastocks.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    /** GET all stocks
     * @return all stocks at api/stocks
     */
    @GetMapping("")
    List<StockDTO> findAll() {
        return stockService.getAllStocks();
    }

    /** GET stock by id
     * @param stockId stock id
     * @return first occurrence of a stock by that id or 404 if not found
     */
    @GetMapping("/id")
    StockDTO findById(
            @RequestParam(name="stockId", required=true) final Integer stockId
    ) {
        return stockService.getStockById(stockId);
    }

    /** GET stock by symbol
     * @param symbol symbol
     * @return first occurrence of a stock by that symbol or 404 if not found
     */
    @GetMapping("/symbol")
    StockDTO findBySymbol(
            @RequestParam(name="symbol", required=true) final String symbol
    ) {
        return stockService.getStockBySymbol(symbol);
    }

    /** CREATE stock
     * @param stock json object in the body with HTTP 201
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    Integer create(@Valid @RequestBody Stock stock) {
        return stockService.createStock(stock);
    }

    /** DELETE stock by id
     * @param stockId stock id
     * @return confirmation string
     */
    @DeleteMapping("")
    String delete(
            @RequestParam(name="stockId", required=true) final Integer stockId
    ) {
        return stockService.deleteStock(stockId);
    }
}
