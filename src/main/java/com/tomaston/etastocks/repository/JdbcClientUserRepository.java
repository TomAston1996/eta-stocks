package com.tomaston.etastocks.repository;

import com.tomaston.etastocks.domain.User;
import com.tomaston.etastocks.exception.ApiRequestException;
import com.tomaston.etastocks.exception.NotFoundRequestException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientUserRepository {

    private final JdbcClient jdbcClient;

    public JdbcClientUserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /** GET all users from postgres users table
     * @return list of all users
     */
    public List<User> findAll() {
        return jdbcClient.sql("SELECT * FROM users")
                .query(User.class)
                .list();
    }

    /** GET individual user by id
     * @param userId user id
     * @return user record from users table
     */
    public Optional<User> findById(Integer userId) {
        Optional<User> user =  jdbcClient.sql("SELECT userId, email, created_on, pass FROM users WHERE userId = :userId" )
                .param("userId", userId)
                .query(User.class)
                .optional();

        if (user.isEmpty()) {
            throw new NotFoundRequestException("User with id {" + userId + "} not found...");
        }

        return user;
    }

    /** CREATE a new user in the users table
     * @param user object supplied by client with email and password
     * @return confirmation string or 400/500
     */
    public String create(User user) {
        if (user.email() == null || user.pass() == null) {
            throw new ApiRequestException("Either email or password is missing...");
        }

        LocalDateTime now = LocalDateTime.now();
        var updated = jdbcClient.sql("INSERT INTO users(email, created_on, pass) VALUES(?,?,?)")
                .params(List.of(user.email(), now, user.pass()))
                .update();

        Assert.state(updated == 1, "Failed to create user " + user.email());

        return "User '" + user.email() + "' created!";
    }

    /** UPDATE user by id
     * @param user object supplied by client= with info to be updated
     * @param userId specific user id
     */
    public void update(User user, Integer userId) {
        findById(userId); // will throw 404 if not found
        var updated = jdbcClient.sql("UPDATE users SET email = ?, pass = ? WHERE userId = ?")
                .params(List.of(user.email(), user.pass(), userId))
                .update();

        Assert.state(updated == 1, "Failed to update user " + userId);
    }

    /** DELETE user
     * @param userId user id to be deleted
     */
    public String delete(Integer userId) {
        findById(userId); // will throw 404 if not found
        var updated = jdbcClient.sql("DELETE FROM users where userId = :userId")
                .param("userId", userId)
                .update();

        Assert.state(updated == 1, "Failed to delete user " + userId);

        return "User " + userId + " deleted...";
    }

}
