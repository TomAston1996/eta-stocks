package com.tomaston.etastocks.controller;

import com.tomaston.etastocks.domain.User;
import com.tomaston.etastocks.dto.UserDTO;
import com.tomaston.etastocks.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /** GET all users
     * @return all users at api/users
     */
    @GetMapping("")
    List<UserDTO> findAll() {
        return userService.getAllUsers();
    }

    /** GET user
     * @param userId user id
     * @return first occurrence of a user by that id or 404 if not found
     */
    @GetMapping("/{userId}")
    UserDTO findById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }

    /** CREATE user
     * @param user json object in the body with HTTP 201
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    String create(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    /** UPDATE user by id
     * @param user user info to be updated
     * @param userId user id
     * @return userDTO object
     */
    @PutMapping("/{userId}")
    UserDTO update(@RequestBody User user, @PathVariable Integer userId) {
        return userService.updateUser(user, userId);
    }

    /** DELETE user by id
     * @param userId user id
     * @return confirmation string
     */
    @DeleteMapping("/{userId}")
    String delete(@PathVariable Integer userId) {
        return userService.deleteUser(userId);
    }
}
