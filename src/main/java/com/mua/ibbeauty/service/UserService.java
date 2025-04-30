package com.mua.ibbeauty.service;

import com.mua.ibbeauty.model.DTO.UserLoginDTO;
import com.mua.ibbeauty.model.DTO.UserRegistrationDTO;
import com.mua.ibbeauty.model.User;

public interface UserService {
    User registerUser(UserRegistrationDTO userRegistrationDTO);
}
