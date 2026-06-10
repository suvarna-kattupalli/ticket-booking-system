package com.Ticketing.ticketing.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    public JSONObject createOrder(Double amount)
            throws Exception {

        RazorpayClient razorpay =
                new RazorpayClient(
                        keyId,
                        keySecret
                );

        JSONObject orderRequest =
                new JSONObject();

        orderRequest.put(
                "amount",
                amount * 100
        );

        orderRequest.put(
                "currency",
                "INR"
        );

        orderRequest.put(
                "receipt",
                "txn_" + System.currentTimeMillis()
        );

        Order order =
                razorpay.orders.create(
                        orderRequest
                );

        return new JSONObject(
                order.toString()
        );
    }
}