// controllers/delete/DeleteCategoriaController.java
package com.puntooficio.puntooficio.categoria.controllers.delete;

import com.puntooficio.puntooficio.categoria.services.interfaces.domain.ICategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class DeleteCategoriaController {

    private final ICategoriaService categoriaService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}