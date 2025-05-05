package com.mua.ibbeauty.service;

import com.mua.ibbeauty.model.DTO.ReservationRequestDTO;
import com.mua.ibbeauty.model.Reservation;
import java.util.List;
import java.util.UUID;

public interface ReservationService {
    List<Reservation> getAllReservations();

    Reservation getReservation(UUID Id);

    Reservation addReservation(ReservationRequestDTO reservationRequestDTO);

    Reservation deleteReservation(UUID Id);

    Reservation editReservation(ReservationRequestDTO reservationRequestDTO);
}
