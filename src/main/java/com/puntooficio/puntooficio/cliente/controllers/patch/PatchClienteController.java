package com.puntooficio.puntooficio.cliente.controllers.patch;

import com.puntooficio.puntooficio.cliente.dtos.request.ClienteRequestDto;
import com.puntooficio.puntooficio.cliente.dtos.response.ClienteResponseDto;
import com.puntooficio.puntooficio.cliente.service.interfaces.domain.IClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class PatchClienteController {

    private final IClienteService clienteService;

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE') and #id == authentication.principal.id")
    public ResponseEntity<ClienteResponseDto> partialUpdate(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDto requestDto) {
        return ResponseEntity.ok(clienteService.partialUpdate(id, requestDto));
    }
}
