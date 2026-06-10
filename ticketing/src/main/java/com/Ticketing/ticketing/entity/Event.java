package com.Ticketing.ticketing.entity;

import jakarta.persistence.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String location;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    private Double price;

    @Column(name = "total_seats")
    private Integer totalSeats;

    @Column(name = "available_seats")
    private Integer availableSeats;

    private String status;
}