package com.puntooficio.puntooficio.resena.controllers.post;

import com.puntooficio.puntooficio.resena.dtos.request.ResenaRequestDto;
import com.puntooficio.puntooficio.resena.dtos.response.ResenaResponseDto;
import com.puntooficio.puntooficio.resena.services.interfaces.domain.IResenaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resenas")
@RequiredArgsConstructor
public class ResenaPostController {

    private final IResenaService resenaService;

    @PostMapping
    public ResponseEntity<ResenaResponseDto> create(@RequestBody ResenaRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resenaService.create(dto));
    }
}
