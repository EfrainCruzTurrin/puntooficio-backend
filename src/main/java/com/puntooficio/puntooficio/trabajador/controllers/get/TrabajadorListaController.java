package com.puntooficio.puntooficio.trabajador.controllers.get;

import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorListaResponseDto;
import com.puntooficio.puntooficio.trabajador.services.interfaces.domain.ITrabajadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trabajadores")
@RequiredArgsConstructor
public class TrabajadorListaController {

    private final ITrabajadorService trabajadorService;

    // Vista resumida (cards): usada en WorkerList del frontend.
    @GetMapping("/lista")
    public ResponseEntity<Page<TrabajadorListaResponseDto>> lista(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) String ciudad,
            Pageable pageable) {
        return ResponseEntity.ok(trabajadorService.getLista(categoriaId, ciudad, pageable));
    }
}
