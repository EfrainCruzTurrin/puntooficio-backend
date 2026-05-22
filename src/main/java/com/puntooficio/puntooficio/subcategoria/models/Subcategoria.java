package com.puntooficio.puntooficio.subcategoria.models;

import com.puntooficio.puntooficio.categoria.models.Categoria;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subcategorias")
@Getter @Setter @NoArgsConstructor
public class Subcategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    // Muchas subcategorías pertenecen a una categoría
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public Subcategoria(String nombre, Categoria categoria) {
        this.nombre = nombre;
        this.categoria = categoria;
    }
}