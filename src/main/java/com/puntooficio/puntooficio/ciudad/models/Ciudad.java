package com.puntooficio.puntooficio.ciudad.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ciudades")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;
}