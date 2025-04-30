package com.mua.ibbeauty.model.DTO;

import jakarta.validation.constraints.NotBlank;


import java.util.UUID;

public record PostRequestDTO (
        UUID Id,
        @NotBlank
        byte[] photo,
        @NotBlank
        String text
){
}