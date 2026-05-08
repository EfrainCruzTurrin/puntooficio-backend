package com.puntooficio.puntooficio.resena.controllers.get;

import com.puntooficio.puntooficio.resena.dtos.response.ResenaResponseDto;
import com.puntooficio.puntooficio.resena.services.interfaces.domain.IResenaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
@RequiredArgsConstructor
public class ResenaGetController {

    private final IResenaService resenaService;

    @GetMapping("/{id}")
    public ResponseEntity<ResenaResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(resenaService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ResenaResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(resenaService.findAll(pageable));
    }

}