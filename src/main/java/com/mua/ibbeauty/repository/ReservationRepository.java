package com.mua.ibbeauty.repository;

import com.mua.ibbeauty.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    @Query("SELECT r FROM Reservation r LEFT JOIN FETCH r.type")
    List<Reservation> findAllWithServices();

    @Query("SELECT r FROM Reservation r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Reservation> findAllByName(@Param("name") String name);

}
