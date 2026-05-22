package com.puntooficio.puntooficio.trabajador.controllers.perfil;

import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorPerfilResponseDto;
import com.puntooficio.puntooficio.trabajador.services.interfaces.domain.ITrabajadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trabajadores")
@RequiredArgsConstructor
public class TrabajadorPerfilController {

    private final ITrabajadorService trabajadorService;

    // Perfil público — no requiere autenticación si la SecurityConfig lo permite.
    @GetMapping("/{id}/perfil")
    public ResponseEntity<TrabajadorPerfilResponseDto> getPerfilPublico(@PathVariable Long id) {
        return ResponseEntity.ok(trabajadorService.getPerfilPublico(id));
    }
}
