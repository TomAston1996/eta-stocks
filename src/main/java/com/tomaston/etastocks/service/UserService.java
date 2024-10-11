package com.tomaston.etastocks.service;

import com.tomaston.etastocks.domain.Stock;
import com.tomaston.etastocks.domain.User;
import com.tomaston.etastocks.dto.UserDTO;
import com.tomaston.etastocks.exception.NotFoundRequestException;
import com.tomaston.etastocks.repository.UserRepository;
import com.tomaston.etastocks.repository.UserStockRepository;
import com.tomaston.etastocks.utils.DateTimeConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    final private UserRepository userRepository;
    final private UserStockRepository userStockRepository;

    public UserService(UserRepository userRepository, UserStockRepository userStockRepository) {
        this.userRepository = userRepository;
        this.userStockRepository = userStockRepository;
    }

    /** GET all users to client response object
     * @return list of all users in the user database (excluding passwords)
     */
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> clientResponse = new ArrayList<>();
        for (User user : users) {
            clientResponse.add(
                    new UserDTO(
                            user.userId(),
                            user.email(),
                            DateTimeConverter.localTimestampToUnix(user.createdOn())
                    )
            );
        }
        return clientResponse;
    }

    /** GET user to client response object
     * @param userId user primary id
     * @return user information (excluding password)
     */
    public UserDTO getUserById(Integer userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new NotFoundRequestException("User with id {" + userId + "} not found...");
        }

        return new UserDTO(
                user.get().userId(),
                user.get().email(),
                DateTimeConverter.localTimestampToUnix(user.get().createdOn())
        );
    }

    /** CREATE user to client response object
     * @param user info (email and password)
     * @return confirmation string
     */
    public String createUser(User user) {
        return userRepository.create(user);
    }

    /** UPDATE user by id
     * @param user user info
     * @param userId user id
     * @return userDTO
     */
    public UserDTO updateUser(User user, Integer userId) {
        userRepository.update(user, userId);
        return getUserById(userId);
    }

    /** DELETE user by id
     * @param userId user id
     * @return confirmation string
     */
    public String deleteUser(Integer userId) {
        return userRepository.delete(userId);
    }

    /** GET all favourite stocks by userId
     * @param userId user id
     * @return list of stock information saved by the user
     */
    public List<Stock> getUserStocksByUserId(Integer userId) {
        return userStockRepository.findAllByUserId(userId);
    }
}
