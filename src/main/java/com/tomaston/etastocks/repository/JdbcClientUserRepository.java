package com.tomaston.etastocks.repository;

import com.tomaston.etastocks.domain.User;
import com.tomaston.etastocks.dto.UserDTO;
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

    public List<User> findAll() {
        return jdbcClient.sql("SELECT * FROM users")
                .query(User.class)
                .list();
    }

    public Optional<User> findById(Integer userId) {
        return jdbcClient.sql("SELECT userId, email, created_on, pass FROM users WHERE userId = :userId" )
                .param("userId", userId)
                .query(User.class)
                .optional();
    }

    public void create(User user) {
        LocalDateTime now = LocalDateTime.now();
        var updated = jdbcClient.sql("INSERT INTO users(email, createdOn, pass) VALUES(?,?,?)")
                .params(List.of(user.email(), now, user.pass()))
                .update();

        Assert.state(updated == 1, "Failed to create run " + user.email());
    }
}
