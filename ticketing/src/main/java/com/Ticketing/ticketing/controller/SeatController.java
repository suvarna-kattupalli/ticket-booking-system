package com.Ticketing.ticketing.controller;

import com.Ticketing.ticketing.entity.Seat;
import com.Ticketing.ticketing.service.SeatService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SeatController {

    private final SeatService seatService;

    // Create Seat
    @PostMapping("/{eventId}")
    public Seat createSeat(
            @PathVariable Long eventId,
            @RequestBody Seat seat
    ) {

        return seatService.createSeat(
                eventId,
                seat
        );
    }

    // Get All Seats
    @GetMapping
    public List<Seat> getAllSeats() {

        return seatService.getAllSeats();
    }
    @GetMapping("/{eventId}")
    public List<Seat> getSeats(@PathVariable Long eventId) {
        return seatService.getSeatsByEvent(eventId);
    }
    @GetMapping("/event/{eventId}")
    public List<Seat> getSeatsByEvent(
            @PathVariable Long eventId
    ) {

        return seatService.getSeatsByEvent(eventId);
    }

    // Get Seat By id
    @GetMapping("/{id}")
    public Seat getSeatById(
            @PathVariable Long id
    ) {

        return seatService.getSeatById(id);
    }

    // Delete Seat
    @DeleteMapping("/{id}")
    public String deleteSeat(
            @PathVariable Long id
    ) {

        seatService.deleteSeat(id);

        return "Seat deleted successfully";
    }
}