package com.puntooficio.puntooficio.resena.controllers.delete;

import com.puntooficio.puntooficio.resena.services.interfaces.domain.IResenaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resenas")
@RequiredArgsConstructor
public class ResenaDeleteController {

    private final IResenaService resenaService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resenaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}