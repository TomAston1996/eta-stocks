package com.tomaston.etastocks.service;

import com.tomaston.etastocks.domain.UserStock;
import com.tomaston.etastocks.exception.NotFoundRequestException;
import com.tomaston.etastocks.repository.StockRepository;
import com.tomaston.etastocks.repository.UserRepository;
import com.tomaston.etastocks.repository.UserStockRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserStockService {

    final private UserStockRepository userStockRepository;
    final private StockRepository stockRepository;
    final private UserRepository userRepository;

    public UserStockService(UserStockRepository userStockRepository, StockRepository stockRepository, UserRepository userRepository) {
        this.userStockRepository = userStockRepository;
        this.stockRepository = stockRepository;
        this.userRepository = userRepository;
    }

    /** GET all user-stocks
     * @return list of all user-stocks in the userstocks database
     */
    public List<UserStock> getAllUserStocks() {
        return userStockRepository.findAll();
    }

    /**
     * GET user-stock by compound id
     * @param stockId stock id
     * @param userId user id
     * @return UserStock object
     */
    public UserStock getUserStockById(Integer stockId, Integer userId) {
        Optional<UserStock> response = userStockRepository.findById(stockId, userId);
        if (response.isEmpty()) {
            throw new NotFoundRequestException("Stock with stockId {" + stockId + "} and userId {" + userId +"} not found...");
        }
        return response.get();
    }

    /** CREATE user-stock relationship
     * @param stockId stock id
     * @param userId user id
     * @return confirmation string
     */
    public String createUserStock(Integer stockId, Integer userId) {
        if (this.userRepository.findById(userId).isEmpty() || this.stockRepository.findById(stockId).isEmpty()) {
            throw new NotFoundRequestException("Either userId or stockId does not exist...");
        }
        return userStockRepository.create(stockId, userId);
    }

    /** DELETE user-stock by id
     * @param stockId stock id
     * @param userId user id
     * @return confirmation string
     */
    public String deleteUserStock(Integer stockId, Integer userId) {
        if (this.userRepository.findById(userId).isEmpty() || this.stockRepository.findById(stockId).isEmpty()) {
            throw new NotFoundRequestException("Either userId or stockId does not exist...");
        }
        return userStockRepository.delete(stockId, userId);
    }
}
