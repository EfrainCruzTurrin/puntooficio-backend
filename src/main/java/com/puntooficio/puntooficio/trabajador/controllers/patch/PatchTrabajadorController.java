// trabajador/controllers/patch/PatchTrabajadorController.java
package com.puntooficio.puntooficio.trabajador.controllers.patch;

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
public class PatchTrabajadorController {

    private final ITrabajadorService trabajadorService;

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('TRABAJADOR') and #id == authentication.principal.id")
    public ResponseEntity<TrabajadorResponseDto> partialUpdate(@PathVariable Long id,
                                                               @Valid @RequestBody TrabajadorRequestDto dto) {
        return ResponseEntity.ok(trabajadorService.partialUpdate(id, dto));
    }
}