package com.Ticketing.ticketing.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {

    private Long bookingId;

    private String seatNumber;

    private String eventTitle;

    private Double amount;

    private String paymentStatus;
}
