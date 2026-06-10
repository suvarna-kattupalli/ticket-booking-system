package com.Ticketing.ticketing.config;

import com.Ticketing.ticketing.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(

            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        System.out.println("JWT FILTER HIT");

        String authHeader =
                request.getHeader("Authorization");

        String token = null;
        String email = null;

        if (authHeader != null
                && authHeader.startsWith("Bearer ")) {

            token = authHeader.substring(7);
            System.out.println("Authorization Header = " + authHeader);
            System.out.println("Token = " + token);
            try {

                email = jwtService.extractEmail(token);
                System.out.println("Received Token = " + token);

                System.out.println("JWT Email = " + email);

            } catch (Exception e) {

                e.printStackTrace();

                System.out.println(
                        "Invalid JWT Token: "
                                + e.getMessage()
                );

                filterChain.doFilter(request, response);
                return;
            }
        }

        if (email != null
                && SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {

            UserDetails userDetails =
                    userDetailsService
                            .loadUserByUsername(email);

            System.out.println("UserDetails Username = "
                    + userDetails.getUsername());

            boolean valid =
                    jwtService.validateToken(
                            token,
                            userDetails.getUsername());

            System.out.println("Token Valid = " + valid);

            if (valid) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
                System.out.println(
                        "AUTH SET = " +
                                SecurityContextHolder
                                        .getContext()
                                        .getAuthentication()
                );

                System.out.println("Authentication successful");
            }



            }


        filterChain.doFilter(request, response);
    }
}