package com.Ticketing.ticketing.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponse {

    private Long id;

    private String title;

    private String venue;

    private LocalDateTime eventDate;

    private Double price;
}