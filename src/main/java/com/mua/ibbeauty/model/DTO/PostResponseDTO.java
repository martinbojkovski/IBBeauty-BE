package com.mua.ibbeauty.model.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record PostResponseDTO(
        UUID Id,
        byte[] photo,
        String text,
        LocalDate datePublished
){
}
