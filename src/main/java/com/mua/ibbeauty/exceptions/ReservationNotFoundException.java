package com.mua.ibbeauty.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReservationNotFoundException extends RuntimeException{
    public ReservationNotFoundException(UUID Id) {
        super("The Reservation with Id: " + Id + " doesn't exist");
    }
}
