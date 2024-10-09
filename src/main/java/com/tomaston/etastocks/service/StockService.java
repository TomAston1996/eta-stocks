package com.tomaston.etastocks.service;

import com.tomaston.etastocks.domain.Stock;
import com.tomaston.etastocks.dto.StockDTO;
import com.tomaston.etastocks.exception.NotFoundRequestException;
import com.tomaston.etastocks.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    final private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /** GET all stocks to client response object
     * @return list of all stocks in stocks database
     */
    public List<StockDTO> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        List<StockDTO> clientResponse = new ArrayList<>();
        for (Stock stock : stocks) {
            clientResponse.add(this.convertStockToDTO(stock));
        }
        return clientResponse;
    }

    /** GET stock to client response object
     * @param stockId stock primary id
     * @return stock information
     */
    public StockDTO getStockById(Integer stockId) {
        Optional<Stock> stock = stockRepository.findById(stockId);
        if (stock.isEmpty()) {
            throw new NotFoundRequestException("Stock with id {" + stockId + "} not found...");
        }
        return this.convertStockToDTO(stock.get());
    }

    /** GET stock to client response object
     * @param symbol stock symbol i.e. QQQ
     * @return stock information
     */
    public StockDTO getStockBySymbol(String symbol) {
        Optional<Stock> stock = stockRepository.findBySymbol(symbol);
        if (stock.isEmpty()) {
            throw new NotFoundRequestException("Stock with symbol {" + symbol + "} not found...");
        }
        return this.convertStockToDTO(stock.get());
    }

    /** CREATE stock to client response object
     * @param stock info from AV
     * @return auto-generated stock id
     */
    public Integer createStock(Stock stock) {
        return stockRepository.create(stock);
    }

    /** DELETE stock by id
     * @param stockId stock id
     * @return confirmation string
     */
    public String deleteStock(Integer stockId) {
        return stockRepository.delete(stockId);
    }

    /**
     * Converts stock class to DTO
     * @param stock class
     * @return domain transfer object
     */
    private StockDTO convertStockToDTO(Stock stock) {
        return new StockDTO(
                stock.stockId(),
                stock.symbol(),
                stock.name(),
                stock.type(),
                stock.region(),
                stock.currency()
        );
    }
}
