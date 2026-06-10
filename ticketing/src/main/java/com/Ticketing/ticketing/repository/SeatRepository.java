package com.Ticketing.ticketing.repository;

import com.Ticketing.ticketing.entity.Seat;
import com.Ticketing.ticketing.util.SeatStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByEventId(Long eventId);

    Optional<Seat> findBySeatNumber(String seatNumber);
    long countByEventIdAndStatus(
            Long eventId,
            SeatStatus status
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Seat s WHERE s.id = :id")
    Optional<Seat> findSeatForUpdate(Long id);
}