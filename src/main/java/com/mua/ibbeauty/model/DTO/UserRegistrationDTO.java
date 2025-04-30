package com.mua.ibbeauty.model.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserRegistrationDTO(
        UUID Id,
        @NotBlank
        String userName,
        @NotBlank
        String password,
        @NotBlank
        String role
) {
}
