package com.mua.ibbeauty.model;

import com.mua.ibbeauty.model.enums.Service;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;

    private String name;

    private LocalDateTime reservationStart;

    private LocalDateTime reservationEnd;

    @ElementCollection(targetClass = Service.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "reservation_services", joinColumns = @JoinColumn(name = "reservation_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "service_type")
    private List<Service> type;

    private String description;
}
