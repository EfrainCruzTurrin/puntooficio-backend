package com.puntooficio.puntooficio.resena.controllers.get;

import com.puntooficio.puntooficio.resena.dtos.response.ResenaResponseDto;
import com.puntooficio.puntooficio.resena.services.interfaces.domain.IResenaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resenas")
@RequiredArgsConstructor
public class GetResenaByTrabajadorController {

    private final IResenaService resenaService;

    @GetMapping("/trabajador/{trabajadorId}")
    public ResponseEntity<Page<ResenaResponseDto>> getByTrabajador(
            @PathVariable Long trabajadorId,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(resenaService.findByTrabajadorId(trabajadorId, pageable));
    }
}
