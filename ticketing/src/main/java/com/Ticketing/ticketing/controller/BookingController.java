package com.Ticketing.ticketing.controller;

import com.Ticketing.ticketing.dto.request.BookingRequest;
import com.Ticketing.ticketing.dto.response.BookingResponse;
import com.Ticketing.ticketing.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BookingController {

    private final BookingService bookingService;
    @GetMapping("/my-bookings")
    public ResponseEntity<?> getMyBookings(
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                bookingService.getMyBookings(authentication)
        );
    }

    @PostMapping
    public ResponseEntity<?> bookSeat(
            @RequestBody BookingRequest request,
            Authentication authentication
    ) {

        System.out.println(
                "CONTROLLER AUTH = "
                        + authentication
        );

        return ResponseEntity.ok(
                bookingService.bookSeat(
                        request,
                        authentication
                )
        );
    }
}