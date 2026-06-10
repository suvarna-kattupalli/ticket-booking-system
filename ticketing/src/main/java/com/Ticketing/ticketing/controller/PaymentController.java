package com.Ticketing.ticketing.controller;

import com.Ticketing.ticketing.dto.request.PaymentRequest;
import com.Ticketing.ticketing.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(
            @RequestBody PaymentRequest request
    ) throws Exception {

        JSONObject order =
                paymentService.createOrder(
                        request.getAmount()
                );

        return ResponseEntity.ok(order.toString());
    }
}