package com.puntooficio.puntooficio.galeria.repositories;

import com.puntooficio.puntooficio.galeria.models.GaleriaImagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GaleriaImagenRepository extends JpaRepository<GaleriaImagen, Long> {
    List<GaleriaImagen> findByTrabajadorId(Long trabajadorId);
    long countByTrabajadorId(Long trabajadorId);
}
