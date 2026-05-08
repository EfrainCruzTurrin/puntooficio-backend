package com.puntooficio.puntooficio.auth.controllers;

import com.puntooficio.puntooficio.auth.dtos.request.ClienteRegisterRequestDto;
import com.puntooficio.puntooficio.auth.dtos.request.LoginRequestDto;
import com.puntooficio.puntooficio.auth.dtos.request.TrabajadorRegisterRequestDto;
import com.puntooficio.puntooficio.auth.dtos.response.AuthResponseDto;
import com.puntooficio.puntooficio.auth.services.interfaces.IAuthService;
import com.puntooficio.puntooficio.shared.userdetails.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/register/cliente")
    public ResponseEntity<AuthResponseDto> registerCliente(@RequestBody ClienteRegisterRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerCliente(dto));
    }

    @PostMapping("/register/trabajador")
    public ResponseEntity<AuthResponseDto> registerTrabajador(@RequestBody TrabajadorRegisterRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerTrabajador(dto));
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponseDto> me(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(new AuthResponseDto(null, userDetails.getRole().name(), userDetails.getId()));
    }
}