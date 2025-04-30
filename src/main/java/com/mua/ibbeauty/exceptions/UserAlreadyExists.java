package com.mua.ibbeauty.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@Getter
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UserAlreadyExists extends RuntimeException{
    public UserAlreadyExists(String username) {
        super("The User with username: " + username + " already exist");
    }

}
