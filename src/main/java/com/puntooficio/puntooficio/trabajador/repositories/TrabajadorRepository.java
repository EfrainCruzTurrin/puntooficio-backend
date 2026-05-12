package com.puntooficio.puntooficio.trabajador.repositories;

import com.puntooficio.puntooficio.trabajador.models.Trabajador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {
    Optional<Trabajador> findByTelefono(String telefono);
    boolean existsByTelefono(String telefono);
    boolean existsByDni(String dni);
    Page<Trabajador> findByCategoriaIdAndCiudad(Long categoriaId, String ciudad, Pageable pageable);
    Page<Trabajador> findByCategoriaId(Long categoriaId, Pageable pageable);
    Page<Trabajador> findByCiudad(String ciudad, Pageable pageable);
    Optional<Trabajador> findByEmail(String email);
}