package com.mua.ibbeauty.repository;

import com.mua.ibbeauty.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    Page<Reservation> findAll(Pageable pageable);
}
