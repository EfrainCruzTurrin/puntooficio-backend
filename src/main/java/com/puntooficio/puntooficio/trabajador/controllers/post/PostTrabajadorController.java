// trabajador/controllers/post/PostTrabajadorController.java
package com.puntooficio.puntooficio.trabajador.controllers.post;

import com.puntooficio.puntooficio.trabajador.dtos.request.TrabajadorRequestDto;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorResponseDto;
import com.puntooficio.puntooficio.trabajador.services.interfaces.domain.ITrabajadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trabajadores")
@RequiredArgsConstructor
public class PostTrabajadorController {

    private final ITrabajadorService trabajadorService;

    @PostMapping
    public ResponseEntity<TrabajadorResponseDto> create(@Valid @RequestBody TrabajadorRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trabajadorService.create(dto));
    }
}