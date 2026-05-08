package com.puntooficio.puntooficio.resena.repositories;

import com.puntooficio.puntooficio.resena.models.Resena;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {
    Page<Resena> findByTrabajadorId(Long trabajadorId, Pageable pageable);
}