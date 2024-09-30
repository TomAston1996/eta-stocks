package com.tomaston.etastocks.controller;

import com.tomaston.etastocks.domain.User;
import com.tomaston.etastocks.exception.NotFoundRequestException;
import com.tomaston.etastocks.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /** @return all users at api/users
     */
    @GetMapping("")
    List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * @param userId user id
     * @return first occurrence of a user by that id or 404 if not found
     */
    @GetMapping("/{id}")
    User findById(@PathVariable Integer userId) {
        //Optional class has methods to show if something was returned or not
        //If the optional class is empty then the id wasn't found, and we can throw a 404
        //Otherwise we return the get method
        Optional<User> run = userRepository.findById(userId);
        if (run.isEmpty()) {
            throw new NotFoundRequestException("User not found...");
        }
        return run.get();
    }

    /**
     * @param user json object in the body with HTTP 201
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody User user) {
        userRepository.save(user);
    }
}
