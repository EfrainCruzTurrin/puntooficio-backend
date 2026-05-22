package com.puntooficio.puntooficio.ciudad.repositories;

import com.puntooficio.puntooficio.ciudad.models.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {}