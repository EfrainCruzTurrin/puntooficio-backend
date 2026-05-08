package com.puntooficio.puntooficio.cliente.controllers.put;


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
public class PutClienteController {

    private final IClienteService clienteService;

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE') and #id == authentication.principal.id")
    public ResponseEntity<ClienteResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDto requestDto) {
        return ResponseEntity.ok(clienteService.update(id, requestDto));
    }
}
