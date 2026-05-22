package com.puntooficio.puntooficio.trabajador.controllers.panel;

import com.puntooficio.puntooficio.galeria.dtos.response.GaleriaImagenResponseDto;
import com.puntooficio.puntooficio.shared.userdetails.CustomUserDetails;
import com.puntooficio.puntooficio.trabajador.dtos.request.TrabajadorPanelUpdateRequestDto;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorPerfilResponseDto;
import com.puntooficio.puntooficio.trabajador.services.interfaces.domain.ITrabajadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/trabajadores")
@RequiredArgsConstructor
public class TrabajadorPanelController {

    private final ITrabajadorService trabajadorService;

    // El trabajador autenticado consulta su propio panel.
    // El id sale del JWT vía CustomUserDetails (no se pasa por URL).
    @GetMapping("/mi-perfil")
    @PreAuthorize("hasRole('TRABAJADOR')")
    public ResponseEntity<TrabajadorPerfilResponseDto> getPanel(
            @AuthenticationPrincipal CustomUserDetails principal) {
        return ResponseEntity.ok(trabajadorService.getPanel(principal.getId()));
    }

    @PatchMapping("/mi-perfil")
    @PreAuthorize("hasRole('TRABAJADOR')")
    public ResponseEntity<TrabajadorPerfilResponseDto> updatePanel(
            @AuthenticationPrincipal CustomUserDetails principal,
            @Valid @RequestBody TrabajadorPanelUpdateRequestDto dto) {
        return ResponseEntity.ok(trabajadorService.updatePanel(principal.getId(), dto));
    }

    @PostMapping("/mi-perfil/galeria")
    @PreAuthorize("hasRole('TRABAJADOR')")
    public ResponseEntity<GaleriaImagenResponseDto> agregarImagen(
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(trabajadorService.agregarImagenGaleria(principal.getId(), body.get("url")));
    }

    @DeleteMapping("/mi-perfil/galeria/{imagenId}")
    @PreAuthorize("hasRole('TRABAJADOR')")
    public ResponseEntity<Void> eliminarImagen(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable Long imagenId) {
        trabajadorService.eliminarImagenGaleria(principal.getId(), imagenId);
        return ResponseEntity.noContent().build();
    }


}
