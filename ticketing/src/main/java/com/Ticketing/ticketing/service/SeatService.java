package com.Ticketing.ticketing.service;

import com.Ticketing.ticketing.entity.Event;
import com.Ticketing.ticketing.entity.Seat;
import com.Ticketing.ticketing.repository.EventRepository;
import com.Ticketing.ticketing.repository.SeatRepository;
import com.Ticketing.ticketing.util.SeatStatus;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final EventRepository eventRepository;

    // Create Seat
    public Seat createSeat(
            Long eventId,
            Seat seat
    ) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Event not found"
                        )
                );

        seat.setEvent(event);

        seat.setStatus(
                SeatStatus.AVAILABLE
        );

        return seatRepository.save(seat);
    }

    // Get All Seats
    public List<Seat> getSeatsByEvent(Long eventId) {
        return seatRepository.findByEventId(eventId);
    }

    // Get Seat By id
    public Seat getSeatById(Long id) {

        return seatRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Seat not found"
                        )
                );
    }

    // Delete Seat
    public void deleteSeat(Long id) {

        seatRepository.deleteById(id);
    }

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }
}