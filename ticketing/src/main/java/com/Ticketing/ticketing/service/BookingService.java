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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EmailService emailService;


    @Transactional
    public BookingResponse bookSeat(
            BookingRequest request,
            Authentication authentication) throws Exception {

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
        byte[] pdfBytes =
                generateTicketPdf(
                        booking
                );

        emailService.sendTicketWithPdf(
                user.getEmail(),
                event.getTitle(),
                seat.getSeatNumber(),
                pdfBytes
        );
        /*emailService.sendTicketEmail(
                user.getEmail(),
                event.getTitle(),
                seat.getSeatNumber()
        );*/

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .seatNumber(seat.getSeatNumber())
                .eventTitle(event.getTitle())
                .amount(event.getPrice())
                .paymentStatus("SUCCESS")
                .build();
    }
    public List<Booking> getAllBookings() {

        return bookingRepository.findAll();
    }

    public Long getBookingCount() {

        return bookingRepository.count();
    }

    public Double getRevenue() {

        return bookingRepository.findAll()
                .stream()
                .mapToDouble(
                        Booking::getAmount
                )
                .sum();
    }
    private byte[] generateQRCode(String text)
            throws Exception {

        QRCodeWriter qrCodeWriter =
                new QRCodeWriter();

        BitMatrix bitMatrix =
                qrCodeWriter.encode(
                        text,
                        BarcodeFormat.QR_CODE,
                        200,
                        200
                );

        ByteArrayOutputStream pngOutput =
                new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(
                bitMatrix,
                "PNG",
                pngOutput
        );

        return pngOutput.toByteArray();
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
    public ResponseEntity<byte[]> generateTicket(Long bookingId) throws Exception {

            Booking booking =
                    bookingRepository
                            .findById(bookingId)
                            .orElseThrow();
        String qrData =
                "Booking ID: "
                        + booking.getId()
                        + "\nEvent: "
                        + booking.getEvent().getTitle()
                        + "\nSeat: "
                        + booking.getSeat().getSeatNumber();

            ByteArrayOutputStream output =
                    new ByteArrayOutputStream();

            Document document =
                    new Document();

            PdfWriter.getInstance(
                    document,
                    output
            );

            document.open();

        document.add(
                new Paragraph(
                        "=================================="
                )
        );

        document.add(
                new Paragraph(
                        "EVENT TICKET"
                )
        );

        document.add(
                new Paragraph(
                        "=================================="
                )
        );

            document.add(
                    new Paragraph(
                            "Event: "
                                    + booking.getEvent().getTitle()
                    )
            );

            document.add(
                    new Paragraph(
                            "Seat: "
                                    + booking.getSeat().getSeatNumber()
                    )
            );

            document.add(
                    new Paragraph(
                            "Amount: ₹"
                                    + booking.getAmount()
                    )
            );

            document.add(
                    new Paragraph(
                            "Booking ID: "
                                    + booking.getId()
                    )
            );
        document.add(
                new Paragraph(
                        "\nPayment Status: "
                                + booking.getPaymentStatus()
                )
        );

        document.add(
                new Paragraph(
                        "Booking Time: "
                                + booking.getBookingTime()
                )
        );
        document.add(
                new Paragraph(
                        "\nQR DATA:\n"
                                + qrData
                )
        );
        byte[] qrBytes =
                generateQRCode(qrData);

        Image qrImage =
                Image.getInstance(qrBytes);

        qrImage.scaleToFit(
                150,
                150
        );

        document.add(qrImage);


            document.close();

            HttpHeaders headers =
                    new HttpHeaders();

            headers.add(
                    "Content-Disposition",
                    "attachment; filename=ticket.pdf"
            );

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(
                            MediaType.APPLICATION_PDF
                    )
                    .body(
                            output.toByteArray()
                    );

        }
    private byte[] generateTicketPdf(
            Booking booking
    ) throws Exception {

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        Document document =
                new Document();

        PdfWriter.getInstance(
                document,
                output
        );

        document.open();

        document.add(
                new Paragraph(
                        "=================================="
                )
        );

        document.add(
                new Paragraph(
                        "EVENT TICKET"
                )
        );

        document.add(
                new Paragraph(
                        "=================================="
                )
        );
        document.add(
                new Paragraph(
                        "Event: "
                                + booking.getEvent().getTitle()
                )
        );

        document.add(
                new Paragraph(
                        "Seat: "
                                + booking.getSeat().getSeatNumber()
                )
        );
        document.add(
                new Paragraph(
                        "Amount: ₹"
                                + booking.getAmount()
                )
        );
        document.add(
                new Paragraph(
                        "\nPayment Status: "
                                + booking.getPaymentStatus()
                )
        );

        document.add(
                new Paragraph(
                        "Booking Time: "
                                + booking.getBookingTime()
                )
        );


        document.close();

        return output.toByteArray();
    }


    }

