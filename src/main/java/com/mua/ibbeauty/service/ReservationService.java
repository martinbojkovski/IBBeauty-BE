package com.mua.ibbeauty.service;

import com.mua.ibbeauty.model.DTO.PostRequestDTO;
import com.mua.ibbeauty.model.DTO.ReservationRequestDTO;
import com.mua.ibbeauty.model.Post;
import com.mua.ibbeauty.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ReservationService {
    List<Reservation> getAllReservations();

    Reservation getReservation(UUID Id);

    Page<Reservation> getAllReservationsPageable(Pageable pageable);

    Reservation addReservation(ReservationRequestDTO reservationRequestDTO);

    Reservation deleteReservation(UUID Id);

    Reservation editReservation(ReservationRequestDTO reservationRequestDTO);
}
