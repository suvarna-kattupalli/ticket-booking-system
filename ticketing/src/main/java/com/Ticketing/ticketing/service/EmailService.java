package com.Ticketing.ticketing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;



@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;


    public void sendTicketEmail(
            String toEmail,
            String eventName,
            String seatNumber
    ) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(toEmail);

        message.setSubject(
                "Ticket Booking Confirmation"
        );

        message.setText(
                "Your booking is confirmed.\n\n"
                        + "Event: " + eventName + "\n"
                        + "Seat: " + seatNumber
        );

        mailSender.send(message);
    }


    public void sendTicketWithPdf(
            String toEmail,
            String eventName,
            String seatNumber, byte[] pdfBytes
    ) throws Exception {

        MimeMessage message =
                mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(
                        message,
                        true
                );

        helper.setTo(toEmail);

        helper.setSubject(
                "Ticket Confirmation - "
                        + eventName
        );

        helper.setText(
                "Your ticket has been confirmed.\n\n"
                        + "Please find the attached PDF ticket."
        );

        helper.addAttachment(
                "ticket.pdf",
                new ByteArrayResource(
                        pdfBytes
                )
        );

        mailSender.send(message);
    }
}
