package com.mua.ibbeauty.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(UUID Id) {
        super("The Post with Id: " + Id + " doesn't exist");
    }
}
