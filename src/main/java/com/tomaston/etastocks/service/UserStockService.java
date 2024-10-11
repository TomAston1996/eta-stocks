package com.tomaston.etastocks.service;

import com.tomaston.etastocks.domain.UserStock;
import com.tomaston.etastocks.exception.NotFoundRequestException;
import com.tomaston.etastocks.repository.UserStockRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserStockService {

    final private UserStockRepository userStockRepository;

    public UserStockService(UserStockRepository userStockRepository) {
        this.userStockRepository = userStockRepository;
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
        return userStockRepository.create(stockId, userId);
    }

    /** DELETE user-stock by id
     * @param stockId stock id
     * @param userId user id
     * @return confirmation string
     */
    public String deleteUserStock(Integer stockId, Integer userId) {
        return userStockRepository.delete(stockId, userId);
    }
}
