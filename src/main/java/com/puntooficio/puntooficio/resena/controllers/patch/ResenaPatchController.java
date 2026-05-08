package com.puntooficio.puntooficio.resena.controllers.patch;

import com.puntooficio.puntooficio.resena.dtos.request.ResenaRequestDto;
import com.puntooficio.puntooficio.resena.dtos.response.ResenaResponseDto;
import com.puntooficio.puntooficio.resena.services.interfaces.domain.IResenaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resenas")
@RequiredArgsConstructor
public class ResenaPatchController {

    private final IResenaService resenaService;

    @PatchMapping("/{id}")
    public ResponseEntity<ResenaResponseDto> partialUpdate(@PathVariable Long id, @Valid @RequestBody ResenaRequestDto dto) {
        return ResponseEntity.ok(resenaService.partialUpdate(id, dto));
    }
}