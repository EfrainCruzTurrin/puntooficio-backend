// trabajador/controllers/delete/DeleteTrabajadorController.java
package com.puntooficio.puntooficio.trabajador.controllers.delete;

import com.puntooficio.puntooficio.trabajador.services.interfaces.domain.ITrabajadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trabajadores")
@RequiredArgsConstructor
public class DeleteTrabajadorController {

    private final ITrabajadorService trabajadorService;

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TRABAJADOR') and #id == authentication.principal.id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        trabajadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}