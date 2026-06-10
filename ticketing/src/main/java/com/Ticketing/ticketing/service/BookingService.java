package com.Ticketing.ticketing.service;


import com.Ticketing.ticketing.dto.request.BookingRequest;
import com.Ticketing.ticketing.dto.response.BookingResponse;
import com.Ticketing.ticketing.entity.Booking;
import com.Ticketing.ticketing.entity.Event;
import com.Ticketing.ticketing.entity.Seat;
import com.Ticketing.ticketing.entity.User;
import  com.Ticketing.ticketing.service.SeatLockService;
import com.Ticketing.ticketing.exception.ResourceNotFoundException;
import com.Ticketing.ticketing.repository.BookingRepository;
import com.Ticketing.ticketing.repository.EventRepository;
import com.Ticketing.ticketing.repository.SeatRepository;
import com.Ticketing.ticketing.repository.UserRepository;
import com.Ticketing.ticketing.util.SeatStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public BookingResponse bookSeat(
            BookingRequest request,
            Authentication authentication) {

        if (request.getSeatId() == null) {
            throw new RuntimeException("Seat ID is missing");
        }

        if (request.getEventId() == null) {
            throw new RuntimeException("Event ID is missing");
        }

        if (authentication == null) {
            throw new RuntimeException("User not authenticated");
        }

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Seat seat = seatRepository
                .findSeatForUpdate(request.getSeatId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Seat not found: " + request.getSeatId()));

        Event event = eventRepository
                .findById(request.getEventId())
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        if (seat.getStatus() == SeatStatus.BOOKED) {
            throw new RuntimeException("Seat already booked");
        }

        seat.setStatus(SeatStatus.BOOKED);

        if (event.getAvailableSeats() > 0) {
            event.setAvailableSeats(
                    event.getAvailableSeats() - 1
            );
        }

        seatRepository.save(seat);
        eventRepository.save(event);

        Booking booking = Booking.builder()
                .user(user)
                .event(event)
                .seat(seat)
                .amount(event.getPrice())
                .paymentStatus("SUCCESS")
                .bookingTime(LocalDateTime.now())
                .build();

        booking = bookingRepository.save(booking);

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .seatNumber(seat.getSeatNumber())
                .eventTitle(event.getTitle())
                .amount(event.getPrice())
                .paymentStatus("SUCCESS")
                .build();
    }

    public List<BookingResponse> getMyBookings(
            Authentication authentication) {

        if (authentication == null) {
            throw new RuntimeException("User not authenticated");
        }

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<Booking> bookings =
                bookingRepository.findByUserId(user.getId());

        return bookings.stream()
                .map(booking ->
                        BookingResponse.builder()
                                .bookingId(booking.getId())
                                .seatNumber(
                                        booking.getSeat().getSeatNumber()
                                )
                                .eventTitle(
                                        booking.getEvent().getTitle()
                                )
                                .amount(
                                        booking.getAmount()
                                )
                                .paymentStatus(
                                        booking.getPaymentStatus()
                                )
                                .build()
                )
                .toList();
    }
}