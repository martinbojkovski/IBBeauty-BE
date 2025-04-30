package com.mua.ibbeauty.model.DTO;

public record JwtResponseDTO(
        String token_type,
        String access_token
) {
}
