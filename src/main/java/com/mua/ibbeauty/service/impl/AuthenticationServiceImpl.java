package com.mua.ibbeauty.service.impl;

import com.mua.ibbeauty.jwt.JwtService;
import com.mua.ibbeauty.model.DTO.JwtResponseDTO;
import com.mua.ibbeauty.model.DTO.UserLoginDTO;
import com.mua.ibbeauty.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public JwtResponseDTO generateToken(UserLoginDTO userLoginDTO) throws IOException {
        String username = userLoginDTO.userName();
        String password = userLoginDTO.password();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        String jwtToken = jwtService.generateToken(username);

        return new JwtResponseDTO("Bearer", jwtToken);
    }
}
