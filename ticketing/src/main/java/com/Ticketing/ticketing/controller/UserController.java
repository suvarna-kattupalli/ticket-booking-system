package com.Ticketing.ticketing.controller;


import com.Ticketing.ticketing.entity.User;
import com.Ticketing.ticketing.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService
    ) {

        this.userService = userService;
    }

    @PostMapping
    public User createUser(
            @RequestBody User user
    ) {

        return userService.createUser(user);
    }

    @GetMapping
    public List<User> getUsers() {

        return userService.getAllUsers();
    }
}