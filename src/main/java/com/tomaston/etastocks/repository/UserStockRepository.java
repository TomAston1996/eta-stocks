package com.tomaston.etastocks.repository;

import com.tomaston.etastocks.domain.Stock;
import com.tomaston.etastocks.domain.UserStock;
import com.tomaston.etastocks.exception.AlreadyExistsException;
import com.tomaston.etastocks.exception.NotFoundRequestException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class UserStockRepository {

    private final JdbcClient jdbcClient;

    public UserStockRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /** GET all user-stock relationships from postgres usersstocks table
     * @return list of all user-stocks relationships
     */
    public List<UserStock> findAll() {
        return jdbcClient.sql("SELECT * FROM usersstocks")
                .query(UserStock.class)
                .list();
    }

    /** GET individual favourite stock by userId and stockId
     * @param stockId stock id (compound key)
     * @param userId user id (compound key)
     * @return user-stock record from users-stocks intermediary table
     */
    public Optional<UserStock> findById(Integer stockId, Integer userId) {
        return jdbcClient.sql("SELECT * FROM usersstocks WHERE stockId = ? AND userId = ?")
                .params(List.of(stockId, userId))
                .query(UserStock.class)
                .optional();
    }

    /** GET all favourite stocks and relevant stock info by userId
     * @param userId user id
     * @return all stock ids and info from user-stock table associated with that user
     */
    public List<Stock> findAllByUserId(Integer userId) {
        List<Stock> userStocks =  jdbcClient.sql(
                    "SELECT b.stockid, b.symbol, b.name, b.type, b.region, b.currency FROM usersstocks a JOIN stocks b" +
                    "ON a.stockid = b.stockid WHERE a.userid = :userid"
                )
                .param("userid", userId)
                .query(Stock.class)
                .list();

        if (userStocks.isEmpty()) {
            throw new NotFoundRequestException("No favourite stocks for user with id '" + userId + "' found...");
        }

        return userStocks;
    }

    /** CREATE a new user-stock association in the user-stocks table
     * @param stockId stock id (compound key)
     * @param userId user id (compound key)
     * @return newly created stock id
     */
    public String create(Integer stockId, Integer userId) {
        if (this.findById(stockId, userId).isPresent()) {
            throw new AlreadyExistsException("Stock-user relationship already exists...");
        }

        int updated = jdbcClient.sql("INSERT INTO usersstocks(userid, stockid) VALUES(?,?)")
                .params(List.of(userId, stockId))
                .update();

        Assert.state(updated == 1, "Failed to create user-stock relationship");

        return "Successfully created user-stock relationship between userId {" + userId + "} and stockId {" + stockId + "}";
    }

    /** DELETE stock-user association
     * @param stockId stock id to be deleted (compound key)
     * @param userId user id to be deleted (compound key)
     */
    public String delete(Integer stockId, Integer userId) {
        if (this.findById(stockId, userId).isEmpty()) {
            throw new NotFoundRequestException("User-stock relationship not found...");
        }

        int updated = jdbcClient.sql("DELETE FROM usersstocks WHERE stockId = ? AND userId = ?")
                .params(List.of(stockId, userId))
                .update();

        Assert.state(updated == 1, "Failed to delete stock " + stockId);

        return "User-stock relationship successfully deleted...";
    }
}
