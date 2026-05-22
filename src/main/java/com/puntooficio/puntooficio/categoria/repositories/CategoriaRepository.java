package com.puntooficio.puntooficio.categoria.repositories;

import com.puntooficio.puntooficio.categoria.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Para buscar por nombre exacto (útil para el DataInitializer)
    boolean existsByNombre(String nombre);
}