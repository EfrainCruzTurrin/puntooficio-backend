package com.puntooficio.puntooficio.trabajador.repositories;

import com.puntooficio.puntooficio.trabajador.models.Trabajador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {

    Optional<Trabajador> findByTelefono(String telefono);
    boolean existsByTelefono(String telefono);
    boolean existsByDni(String dni);
    Optional<Trabajador> findByEmail(String email);

    Page<Trabajador> findByCiudad_Nombre(String nombre, Pageable pageable);

    @Query(
            value = "SELECT DISTINCT t FROM Trabajador t JOIN t.subcategorias s WHERE s.categoria.id = :categoriaId",
            countQuery = "SELECT COUNT(DISTINCT t) FROM Trabajador t JOIN t.subcategorias s WHERE s.categoria.id = :categoriaId"
    )
    Page<Trabajador> findByCategoriaId(@Param("categoriaId") Long categoriaId, Pageable pageable);

    @Query(
            value = "SELECT DISTINCT t FROM Trabajador t JOIN t.subcategorias s WHERE s.categoria.id = :categoriaId AND t.ciudad.nombre = :ciudad",
            countQuery = "SELECT COUNT(DISTINCT t) FROM Trabajador t JOIN t.subcategorias s WHERE s.categoria.id = :categoriaId AND t.ciudad.nombre = :ciudad"
    )
    Page<Trabajador> findByCategoriaIdAndCiudad(@Param("categoriaId") Long categoriaId, @Param("ciudad") String ciudad, Pageable pageable);
}