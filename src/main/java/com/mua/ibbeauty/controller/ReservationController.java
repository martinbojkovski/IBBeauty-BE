package com.mua.ibbeauty.controller;

import com.mua.ibbeauty.model.DTO.ReservationRequestDTO;
import com.mua.ibbeauty.model.Reservation;
import com.mua.ibbeauty.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
@CrossOrigin
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations(){
        List<Reservation> result = reservationService.getAllReservations();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<Reservation> getPost(@PathVariable UUID reservationId){
        Reservation result = reservationService.getReservation(reservationId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Reservation>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(reservationService.getAllReservationsPageable(pageable));
    }

    @PostMapping("/insert")
    public ResponseEntity<Reservation> insertReservation(@RequestBody ReservationRequestDTO reservationRequestDTO){
        Reservation response = reservationService.addReservation(reservationRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/edit")
    public ResponseEntity<Reservation> editPost(@RequestBody ReservationRequestDTO reservationRequestDTO){
        Reservation response = reservationService.editReservation(reservationRequestDTO);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<Reservation> deletePost(@PathVariable UUID Id){
        Reservation response = reservationService.deleteReservation(Id);

        return ResponseEntity.ok(response);
    }
}
