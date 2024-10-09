package com.tomaston.etastocks.repository;

import com.tomaston.etastocks.domain.UserStock;
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

    /** GET individual favourite stock by userId and stockId
     * @param stockId stock id
     * @param userId user id
     * @return user-stock record from users-stocks intermediary table
     */
    public Optional<UserStock> findById(Integer stockId, Integer userId) {
        Optional<UserStock> userStock =  jdbcClient.sql("SELECT * FROM usersstocks WHERE stockId = ? AND userId = ?")
                .params(List.of(stockId, userId))
                .query(UserStock.class)
                .optional();

        if (userStock.isEmpty()) {
            throw new NotFoundRequestException("Stock with stockId {" + stockId + "} and userId {" + userId +"} not found...");
        }

        return userStock;
    }

    /** GET all favourite stockIds by userId
     * @param userId user id
     * @return all stock ids from user-stock table associated with that user
     */
    public List<UserStock> findAllByUserId(Integer userId) {
        List<UserStock> userStocks =  jdbcClient.sql("SELECT * FROM usersstocks WHERE userid = :userid")
                .param("userid", userId)
                .query(UserStock.class)
                .list();

        if (userStocks.isEmpty()) {
            throw new NotFoundRequestException("No favourite stocks for user with id '" + userId + "' found...");
        }

        return userStocks;
    }

    /** CREATE a new user-stock association in the user-stocks table
     * @param stockId stock id
     * @param userId user id
     * @return newly created stock id
     */
    public String create(Integer stockId, Integer userId) {
        int updated = jdbcClient.sql("INSERT INTO usersstocks(userid, stockid) VALUES(?,?)")
                .params(List.of(userId, stockId))
                .update();

        Assert.state(updated == 1, "Failed to create user-stock relationship");

        return "Successfully created user-stock relationship between userId {" + userId + "} and stockId {" + stockId + "}";
    }
}
