package com.mua.ibbeauty.repository;

import com.mua.ibbeauty.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    @Query("SELECT r FROM Reservation r LEFT JOIN FETCH r.type")
    List<Reservation> findAllWithServices();

}
