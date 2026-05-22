package com.puntooficio.puntooficio.galeria.models;


import com.puntooficio.puntooficio.trabajador.models.Trabajador;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "galeria_imagenes")
@Getter @Setter @NoArgsConstructor
public class GaleriaImagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // URL de la imagen (puede ser una URL externa o una ruta local /uploads/...)
    @Column(nullable = false)
    private String url;

    // Cada imagen pertenece a un trabajador
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trabajador_id", nullable = false)
    private Trabajador trabajador;

    public GaleriaImagen(String url, Trabajador trabajador) {
        this.url = url;
        this.trabajador = trabajador;
    }
}