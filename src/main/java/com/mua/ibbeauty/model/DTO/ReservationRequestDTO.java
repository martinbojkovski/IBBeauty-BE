package com.mua.ibbeauty.model.DTO;

import com.mua.ibbeauty.model.enums.Service;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReservationRequestDTO(
         UUID Id,
         @NotBlank
         String name,
         @NotBlank
         LocalDateTime start,
         @NotBlank
         LocalDateTime end,
         @NotBlank
         List<Service> type,
         String description
) {
}
