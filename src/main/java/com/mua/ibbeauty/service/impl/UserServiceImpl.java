package com.mua.ibbeauty.service.impl;

import com.mua.ibbeauty.exceptions.UserAlreadyExists;
import com.mua.ibbeauty.exceptions.UserNotFoundException;
import com.mua.ibbeauty.model.DTO.UserRegistrationDTO;
import com.mua.ibbeauty.model.User;
import com.mua.ibbeauty.model.enums.UserRole;
import com.mua.ibbeauty.repository.UserRepository;
import com.mua.ibbeauty.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);

        return user.orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public User registerUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();

        if (userRepository.findByUserName(userRegistrationDTO.userName()).isPresent()) {
            throw new UserAlreadyExists(userRegistrationDTO.userName());
        }

        user.setUserName(userRegistrationDTO.userName());
        String encodedPassword = passwordEncoder.encode(userRegistrationDTO.password());
        user.setPassword(encodedPassword);
        user.setRole(UserRole.valueOf(userRegistrationDTO.role()));

        userRepository.save(user);

        return userRepository.findAll().getLast();
    }
}
