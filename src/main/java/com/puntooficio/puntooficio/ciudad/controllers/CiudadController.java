package com.puntooficio.puntooficio.ciudad.controllers;

import com.puntooficio.puntooficio.ciudad.models.Ciudad;
import com.puntooficio.puntooficio.ciudad.repositories.CiudadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ciudades")
@RequiredArgsConstructor
public class CiudadController {

    private final CiudadRepository ciudadRepository;

    @GetMapping
    public ResponseEntity<List<Ciudad>> listar() {
        return ResponseEntity.ok(ciudadRepository.findAll());
    }
}