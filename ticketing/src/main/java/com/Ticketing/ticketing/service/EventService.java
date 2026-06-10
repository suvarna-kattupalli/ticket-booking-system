package com.Ticketing.ticketing.service;

import com.Ticketing.ticketing.entity.Event;
import com.Ticketing.ticketing.entity.Seat;
import com.Ticketing.ticketing.repository.EventRepository;
import com.Ticketing.ticketing.repository.SeatRepository;
import com.Ticketing.ticketing.util.SeatStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SeatRepository seatRepository;
    public Event getEventById(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        long availableSeats =
                seatRepository.countByEventIdAndStatus(
                        id,
                        SeatStatus.AVAILABLE
                );

        event.setAvailableSeats((int) availableSeats);
        return event;
    }

    // Get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Create event
    @Transactional
    public Event createEvent(Event event) {

        event.setAvailableSeats(
                event.getTotalSeats()
        );

        Event savedEvent = eventRepository.save(event);

        List<Seat> seats = new ArrayList<>();

        for (int i = 1; i <= event.getTotalSeats(); i++) {

            Seat seat = new Seat();

            seat.setSeatNumber("A" + i);
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setEvent(savedEvent);

            seats.add(seat);
        }

        seatRepository.saveAll(seats);

        return savedEvent;
    }
}