package com.Ticketing.ticketing.service;


import com.Ticketing.ticketing.entity.User;
import com.Ticketing.ticketing.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor Injection
    public UserService(
            UserRepository userRepository
    ) {

        this.userRepository = userRepository;
    }

    // Create User
    public User createUser(
            User user
    ) {

        return userRepository.save(user);
    }

    // Get All Users
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    // Get User By Id
    public User getUserById(
            Long id
    ) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );
    }

    // Update User
    public User updateUser(
            Long id,
            User updatedUser
    ) {

        User existingUser =
                userRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        existingUser.setName(
                updatedUser.getName()
        );

        existingUser.setEmail(
                updatedUser.getEmail()
        );

        existingUser.setPassword(
                updatedUser.getPassword()
        );

        existingUser.setRole(
                updatedUser.getRole()
        );

        return userRepository.save(
                existingUser
        );
    }

    // Delete User
    public void deleteUser(
            Long id
    ) {

        userRepository.deleteById(id);
    }
}