// trabajador/controllers/get/GetTrabajadorController.java
package com.puntooficio.puntooficio.trabajador.controllers.get;

import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorResponseDto;
import com.puntooficio.puntooficio.trabajador.services.interfaces.domain.ITrabajadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trabajadores")
@RequiredArgsConstructor
public class GetTrabajadorController {

    private final ITrabajadorService trabajadorService;

    // GetTrabajadorController


    @GetMapping("/{id}")
    public ResponseEntity<TrabajadorResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(trabajadorService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<TrabajadorResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(trabajadorService.findAll(pageable));
    }
}