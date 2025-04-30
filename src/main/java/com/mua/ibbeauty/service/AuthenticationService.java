package com.mua.ibbeauty.service;

import com.mua.ibbeauty.model.DTO.JwtResponseDTO;
import com.mua.ibbeauty.model.DTO.UserLoginDTO;

import java.io.IOException;

public interface AuthenticationService {
    JwtResponseDTO generateToken(UserLoginDTO userLoginDTO) throws IOException;
}
