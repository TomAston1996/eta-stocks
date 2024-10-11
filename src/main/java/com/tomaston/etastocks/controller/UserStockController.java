package com.tomaston.etastocks.controller;

import com.tomaston.etastocks.domain.User;
import com.tomaston.etastocks.domain.UserStock;
import com.tomaston.etastocks.dto.StockDTO;
import com.tomaston.etastocks.service.UserStockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userStock")
public class UserStockController {

    private final UserStockService userStockService;

    public UserStockController(UserStockService userStockService) {
        this.userStockService = userStockService;
    }

    /** GET all user-stocks
     * @return all users stocks at api/userStock
     */
    @GetMapping("")
    List<UserStock> findAll() {
        return userStockService.getAllUserStocks();
    }

    /** GET user-stock by id
     * @param stockId stock id
     * @param userId user id
     * @return first occurrence of a user-stock by that id or 404 if not found
     */
    @GetMapping("/id")
    UserStock findById(
            @RequestParam(name="stockId", required=true) final Integer stockId,
            @RequestParam(name="userId", required=true) final Integer userId
    ) {
        return userStockService.getUserStockById(stockId, userId);
    }

    /** CREATE user
     * @param stockId stock id
     * @param userId user id
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    String create(
            @RequestParam(name="stockId", required=true) final Integer stockId,
            @RequestParam(name="userId", required=true) final Integer userId
    ) {
        return userStockService.createUserStock(stockId, userId);
    }

    /** DELETE user
     * @param stockId stock id
     * @param userId user id
     */
    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping("")
    String delete(
            @RequestParam(name="stockId", required=true) final Integer stockId,
            @RequestParam(name="userId", required=true) final Integer userId
    ) {
        return userStockService.deleteUserStock(stockId, userId);
    }
}
