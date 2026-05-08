package com.puntooficio.puntooficio.trabajador.models;

import com.puntooficio.puntooficio.categoria.models.Categoria;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trabajadores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trabajador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false, unique = true)
    private String telefono;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean perfilVerificado;

    @Column(nullable = false, unique = true)
    private String dni;

    private String fotoPerfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}