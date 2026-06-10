package com.Ticketing.ticketing.service;




import com.Ticketing.ticketing.dto.request.LoginRequest;
import com.Ticketing.ticketing.dto.request.RegisterRequest;
import com.Ticketing.ticketing.dto.response.AuthResponse;
import com.Ticketing.ticketing.entity.User;
import com.Ticketing.ticketing.repository.UserRepository;
import com.Ticketing.ticketing.util.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    // REGISTER
    public AuthResponse register(RegisterRequest request) {

        // Check email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create user
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        // Set role
        user.setRole(
                Role.valueOf(request.getRole().toUpperCase())
        );

        // Save user
        userRepository.save(user);

        // RETURN RESPONSE ONLY
        return new AuthResponse(
                null,
                "User Registered Successfully"
        );
    }
    // LOGIN
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        boolean matches = request.getPassword()
                .equals(user.getPassword());

        if (!matches) {
            throw new RuntimeException("Invalid Password");
        }

        // GENERATE JWT TOKEN
        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new AuthResponse(
                token,
                "Login Successful"
        );
    }
}