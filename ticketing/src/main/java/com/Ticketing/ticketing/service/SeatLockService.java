package com.Ticketing.ticketing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Service
@RequiredArgsConstructor
public class SeatLockService {

    private final StringRedisTemplate redisTemplate;


    public boolean lockSeat(String seatId) {

        Boolean success =
                redisTemplate.opsForValue()
                        .setIfAbsent(
                                "seat:" + seatId,
                                "LOCKED",
                                Duration.ofMinutes(5)
                        );

        return Boolean.TRUE.equals(success);
    }

    public void unlockSeat(String seatId) {

        redisTemplate.delete("seat:" + seatId);
    }
}