package com.mua.ibbeauty.model.DTO;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @NotBlank
        String userName,
        @NotBlank
        String password
) {
}
