package com.puntooficio.puntooficio.cliente.controllers.post;


import com.puntooficio.puntooficio.cliente.dtos.request.ClienteRequestDto;
import com.puntooficio.puntooficio.cliente.dtos.response.ClienteResponseDto;
import com.puntooficio.puntooficio.cliente.service.interfaces.domain.IClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class PostClienteController {

    private final IClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDto> create(@Valid @RequestBody ClienteRequestDto requestDto) {
        ClienteResponseDto response = clienteService.create(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}