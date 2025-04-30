package com.mua.ibbeauty.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;

    private byte[] photo;

    private String text;

    private LocalDateTime datePublished = LocalDateTime.now();
}
