package com.puntooficio.puntooficio.subcategoria.controllers.get;

import com.puntooficio.puntooficio.subcategoria.dtos.response.SubcategoriaResponseDto;
import com.puntooficio.puntooficio.subcategoria.services.interfaces.domain.ISubcategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategorias")
@RequiredArgsConstructor
public class GetSubcategoriaController {

    private final ISubcategoriaService subcategoriaService;

    @GetMapping
    public ResponseEntity<List<SubcategoriaResponseDto>> findAll() {
        return ResponseEntity.ok(subcategoriaService.findAll());
    }

    @GetMapping("/por-categoria/{categoriaId}")
    public ResponseEntity<List<SubcategoriaResponseDto>> findByCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(subcategoriaService.findByCategoriaId(categoriaId));
    }
}
