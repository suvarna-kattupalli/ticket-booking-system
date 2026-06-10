package com.Ticketing.ticketing.entity;

import com.Ticketing.ticketing.util.SeatStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_number")
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public Seat() {
    }

    // Getter for id
    public Long getId() {
        return id;
    }

    // Setter for id
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for seatNumber
    public String getSeatNumber() {
        return seatNumber;
    }

    // Setter for seatNumber
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    // Getter for status
    public SeatStatus getStatus() {
        return status;
    }

    // Setter for status
    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    // Getter for event
    public Event getEvent() {
        return event;
    }

    // Setter for event
    public void setEvent(Event event) {
        this.event = event;
    }
}