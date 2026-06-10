package com.Ticketing.ticketing.entity;

import jakarta.persistence.*;

import java.sql.ConnectionBuilder;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@Data
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User Mapping
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Event Mapping
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    // Seat Mapping
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    // Booking Amount
    private Double amount;

    // Payment Status
    @Column(name = "payment_status")
    private String paymentStatus;

    // Booking Time
    @Column(name = "booking_time")
    private LocalDateTime bookingTime;

    // Default Constructor
    public Booking() {
    }

    // Parameterized Constructor
    public Booking(
            Long id,
            User user,
            Event event,
            Seat seat,
            Double amount,
            String paymentStatus,
            LocalDateTime bookingTime
    ) {

        this.id = id;
        this.user = user;
        this.event = event;
        this.seat = seat;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.bookingTime = bookingTime;
    }

    // Getter for id
    public Long getId() {
        return id;
    }

    // Setter for id
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for user
    public User getUser() {
        return user;
    }

    // Setter for user
    public void setUser(User user) {
        this.user = user;
    }

    // Getter for event
    public Event getEvent() {
        return event;
    }

    // Setter for event
    public void setEvent(Event event) {
        this.event = event;
    }

    // Getter for seat
    public Seat getSeat() {
        return seat;
    }

    // Setter for seat
    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    // Getter for amount
    public Double getAmount() {
        return amount;
    }

    // Setter for amount
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    // Getter for paymentStatus
    public String getPaymentStatus() {
        return paymentStatus;
    }

    // Setter for paymentStatus
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // Getter for bookingTime
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    // Setter for bookingTime
    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
}