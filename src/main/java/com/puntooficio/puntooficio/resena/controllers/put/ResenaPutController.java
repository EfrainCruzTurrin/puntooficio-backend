package com.puntooficio.puntooficio.resena.controllers.put;


import com.puntooficio.puntooficio.resena.dtos.request.ResenaRequestDto;
import com.puntooficio.puntooficio.resena.dtos.response.ResenaResponseDto;
import com.puntooficio.puntooficio.resena.services.interfaces.domain.IResenaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resenas")
@RequiredArgsConstructor
public class ResenaPutController {

    private final IResenaService resenaService;

    @PutMapping("/{id}")
    public ResponseEntity<ResenaResponseDto> update(@PathVariable Long id, @RequestBody ResenaRequestDto dto) {
        return ResponseEntity.ok(resenaService.update(id, dto));
    }
}