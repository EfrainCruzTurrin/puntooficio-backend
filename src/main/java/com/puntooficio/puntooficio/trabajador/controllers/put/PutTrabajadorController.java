// trabajador/controllers/put/PutTrabajadorController.java
package com.puntooficio.puntooficio.trabajador.controllers.put;

import com.puntooficio.puntooficio.trabajador.dtos.request.TrabajadorRequestDto;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorResponseDto;
import com.puntooficio.puntooficio.trabajador.services.interfaces.domain.ITrabajadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trabajadores")
@RequiredArgsConstructor
public class PutTrabajadorController {

    private final ITrabajadorService trabajadorService;

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TRABAJADOR') and #id == authentication.principal.id")
    public ResponseEntity<TrabajadorResponseDto> update(@PathVariable Long id,
                                                        @Valid @RequestBody TrabajadorRequestDto dto) {
        return ResponseEntity.ok(trabajadorService.update(id, dto));
    }
}