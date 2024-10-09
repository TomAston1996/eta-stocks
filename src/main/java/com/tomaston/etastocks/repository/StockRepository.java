package com.tomaston.etastocks.repository;

import com.tomaston.etastocks.domain.Stock;
import com.tomaston.etastocks.exception.AlreadyExistsException;
import com.tomaston.etastocks.exception.NotFoundRequestException;
import com.tomaston.etastocks.exception.ServerErrorException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class StockRepository {

    private final JdbcClient jdbcClient;

    public StockRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /** GET all stocks from postgres stocks table
     * @return list of all stocks
     */
    public List<Stock> findAll() {
        return jdbcClient.sql("SELECT * FROM stocks")
                .query(Stock.class)
                .list();
    }

    /** GET individual stock by id
     * @param stockId stock id
     * @return stock record from stocks table
     */
    public Optional<Stock> findById(Integer stockId) {
        Optional<Stock> stock =  jdbcClient.sql("SELECT * FROM stocks WHERE stockId = :stockId")
                .param("stockId", stockId)
                .query(Stock.class)
                .optional();

        if (stock.isEmpty()) {
            throw new NotFoundRequestException("Stock with id {" + stockId + "} not found...");
        }

        return stock;
    }

    /** GET individual stock by symbol
     * @param symbol stock symbol
     * @return stock record from stocks table
     */
    public Optional<Stock> findBySymbol(String symbol) {
        return jdbcClient.sql("SELECT * FROM stocks WHERE symbol = :symbol")
                .param("symbol", symbol)
                .query(Stock.class)
                .optional();
    }

    /** CREATE a new stock in the stocks table
     * @param stock object supplied by client with email and password
     * @return newly created stock id
     */
    public Integer create(Stock stock) {
        if (this.findBySymbol(stock.symbol()).isPresent()) {
            throw new AlreadyExistsException("Stock " + stock.symbol() + " already exists in the stocks database...");
        }

        int updated = jdbcClient.sql("INSERT INTO stocks(symbol, name, type, region, currency) VALUES(?,?,?,?,?)")
                .params(List.of(stock.symbol(), stock.name(), stock.type(), stock.region(), stock.currency()))
                .update();

        Assert.state(updated == 1, "Failed to create stock " + stock.symbol());

        Optional<Stock> createStockResponse = this.findBySymbol(stock.symbol());

        if (createStockResponse.isEmpty()) {
            throw new ServerErrorException("Autogenerated key in stocks table not found...");
        } else {
            return createStockResponse.get().stockId();
        }
    }

    /** DELETE stock
     * @param stockId stock id to be deleted
     */
    public String delete(Integer stockId) {
        Optional<Stock> res = this.findById(stockId); // will throw 404 if not found

        int updated = jdbcClient.sql("DELETE FROM stocks where stockId = :stockId")
                .param("stockId", stockId)
                .update();

        Assert.state(updated == 1, "Failed to delete stock " + stockId);

        String stockName = res.isPresent() ? res.get().name() : "{name=NULL}";

        return "Stock " + stockName + " deleted...";
    }
}
