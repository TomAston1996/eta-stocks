package com.tomaston.etastocks.service;

import com.tomaston.etastocks.domain.User;
import com.tomaston.etastocks.dto.UserDTO;
import com.tomaston.etastocks.exception.NotFoundRequestException;
import com.tomaston.etastocks.repository.JdbcClientUserRepository;
import com.tomaston.etastocks.utils.DateTimeConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    final private JdbcClientUserRepository jdbcClientUserRepository;

    public UserService(JdbcClientUserRepository jdbcClientUserRepository) {
        this.jdbcClientUserRepository = jdbcClientUserRepository;
    }

    /** GET all users to client response object
     * @return list of all users in the user database (excluding passwords)
     */
    public List<UserDTO> getAllUsers() {
        List<User> users = jdbcClientUserRepository.findAll();
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
        Optional<User> user = jdbcClientUserRepository.findById(userId);

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
        return jdbcClientUserRepository.create(user);
    }

    /** UPDATE user by id
     * @param user user info
     * @param userId user id
     * @return userDTO
     */
    public UserDTO updateUser(User user, Integer userId) {
        jdbcClientUserRepository.update(user, userId);
        return getUserById(userId);
    }

    /** DELETE user by id
     * @param userId user id
     * @return confirmation string
     */
    public String deleteUser(Integer userId) {
        return jdbcClientUserRepository.delete(userId);
    }
}
