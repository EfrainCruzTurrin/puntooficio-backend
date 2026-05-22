// controllers/get/GetCategoriaController.java
package com.puntooficio.puntooficio.categoria.controllers.get;

import com.puntooficio.puntooficio.categoria.dtos.response.CategoriaConSubcategoriasResponseDto;
import com.puntooficio.puntooficio.categoria.dtos.response.CategoriaResponseDto;
import com.puntooficio.puntooficio.categoria.services.interfaces.domain.ICategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class GetCategoriaController {

    private final ICategoriaService categoriaService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CategoriaResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(categoriaService.findAll(pageable));
    }

    // Endpoint usado por el SubcategoriaSelector del frontend
    @GetMapping("/con-subcategorias")
    public ResponseEntity<List<CategoriaConSubcategoriasResponseDto>> findAllConSubcategorias() {
        return ResponseEntity.ok(categoriaService.findAllConSubcategorias());
    }
}