// controllers/put/PutCategoriaController.java
package com.puntooficio.puntooficio.categoria.controllers.put;

import com.puntooficio.puntooficio.categoria.dtos.request.CategoriaRequestDto;
import com.puntooficio.puntooficio.categoria.dtos.response.CategoriaResponseDto;
import com.puntooficio.puntooficio.categoria.services.interfaces.domain.ICategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class PutCategoriaController {

    private final ICategoriaService categoriaService;

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDto dto) {
        return ResponseEntity.ok(categoriaService.update(id, dto));
    }
}