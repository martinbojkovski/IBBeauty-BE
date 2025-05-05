package com.mua.ibbeauty.controller;

import com.mua.ibbeauty.model.DTO.JwtResponseDTO;
import com.mua.ibbeauty.model.DTO.UserLoginDTO;
import com.mua.ibbeauty.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt")
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/generate")
    public ResponseEntity<JwtResponseDTO> generateJwtToken(@RequestBody UserLoginDTO userLoginDTO) throws IOException {
        JwtResponseDTO response = authenticationService.generateToken(userLoginDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
