package com.mua.ibbeauty.service.impl;

import com.mua.ibbeauty.exceptions.PostNotFoundException;
import com.mua.ibbeauty.exceptions.ReservationNotFoundException;
import com.mua.ibbeauty.model.DTO.ReservationRequestDTO;
import com.mua.ibbeauty.model.Reservation;
import com.mua.ibbeauty.repository.ReservationRepository;
import com.mua.ibbeauty.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservation(UUID Id) {
        return reservationRepository.findById(Id).orElseThrow(() -> new ReservationNotFoundException(Id));
    }

    @Override
    public Page<Reservation> getAllReservationsPageable(Pageable pageable) {
        return reservationRepository.findAll(pageable);
    }

    @Override
    public Reservation addReservation(ReservationRequestDTO reservationRequestDTO) {
        Reservation reservation = new Reservation();
        reservation.setName(reservationRequestDTO.name());
        reservation.setReservationStart(reservationRequestDTO.start());
        reservation.setReservationEnd(reservationRequestDTO.end());
        reservation.setType(reservationRequestDTO.type());
        reservation.setDescription(reservationRequestDTO.description());

        reservationRepository.save(reservation);

        return reservation;
    }

    @Override
    public Reservation deleteReservation(UUID Id) {
        Reservation reservation =
                reservationRepository.findById(Id).orElseThrow(() -> new ReservationNotFoundException(Id));

        reservationRepository.delete(reservation);

        return reservation;
    }

    @Override
    public Reservation editReservation(ReservationRequestDTO reservationRequestDTO) {
        UUID Id = reservationRequestDTO.Id();
        Reservation reservation = reservationRepository.findById(Id)
                .orElseThrow(() -> new ReservationNotFoundException(Id));

        reservation.setName(reservationRequestDTO.name() != null && !reservationRequestDTO.name().isBlank()
                ? reservationRequestDTO.name() : reservation.getName());

        reservation.setDescription(
                reservationRequestDTO.description() != null && !reservationRequestDTO.description().isBlank()
                        ? reservationRequestDTO.description() : reservation.getDescription());

        reservation.setType(reservationRequestDTO.type() != null
                ? reservationRequestDTO.type() : reservation.getType());

        reservation.setReservationStart(reservationRequestDTO.start() != null
                ? reservationRequestDTO.start() : reservation.getReservationStart());

        reservation.setReservationEnd(reservationRequestDTO.end() != null
                ? reservationRequestDTO.end() : reservation.getReservationEnd());

        return reservationRepository.save(reservation);
    }

}
