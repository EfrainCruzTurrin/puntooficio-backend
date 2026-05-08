package com.puntooficio.puntooficio.auth.services.impl;

import com.puntooficio.puntooficio.auth.dtos.request.ClienteRegisterRequestDto;
import com.puntooficio.puntooficio.auth.dtos.request.LoginRequestDto;
import com.puntooficio.puntooficio.auth.dtos.request.TrabajadorRegisterRequestDto;
import com.puntooficio.puntooficio.auth.dtos.response.AuthResponseDto;
import com.puntooficio.puntooficio.auth.services.interfaces.IAuthService;
import com.puntooficio.puntooficio.categoria.models.Categoria;
import com.puntooficio.puntooficio.categoria.repositories.CategoriaRepository;
import com.puntooficio.puntooficio.cliente.models.Cliente;
import com.puntooficio.puntooficio.cliente.repositories.ClienteRepository;
import com.puntooficio.puntooficio.shared.enums.Role;
import com.puntooficio.puntooficio.shared.exceptions.DuplicateResourceException;
import com.puntooficio.puntooficio.shared.exceptions.ResourceNotFoundException;
import com.puntooficio.puntooficio.shared.security.JwtService;
import com.puntooficio.puntooficio.shared.userdetails.CustomUserDetails;
import com.puntooficio.puntooficio.shared.userdetails.CustomUserDetailsService;
import com.puntooficio.puntooficio.trabajador.models.Trabajador;
import com.puntooficio.puntooficio.trabajador.repositories.TrabajadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final ClienteRepository clienteRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final CategoriaRepository categoriaRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    public AuthResponseDto login(LoginRequestDto dto) {
        // Spring Security verifica credenciales y lanza BadCredentialsException si fallan
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getIdentifier(), dto.getPassword())
        );
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(dto.getIdentifier());
        String token = jwtService.generateToken(userDetails);
        return new AuthResponseDto(token, userDetails.getRole().name(), userDetails.getId());
    }

    @Override
    public AuthResponseDto registerCliente(ClienteRegisterRequestDto dto) {
        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Ya existe un cliente con el email: " + dto.getEmail());
        }
        Cliente cliente = new Cliente();
        cliente.setNombreCompleto(dto.getNombreCompleto());
        cliente.setTelefono(dto.getTelefono());
        cliente.setEmail(dto.getEmail());
        cliente.setPassword(passwordEncoder.encode(dto.getPassword()));
        Cliente saved = clienteRepository.save(cliente);

        CustomUserDetails userDetails = new CustomUserDetails(
                saved.getId(), saved.getEmail(), saved.getPassword(), Role.CLIENTE
        );
        return new AuthResponseDto(jwtService.generateToken(userDetails), Role.CLIENTE.name(), saved.getId());
    }

    @Override
    public AuthResponseDto registerTrabajador(TrabajadorRegisterRequestDto dto) {
        if (trabajadorRepository.existsByTelefono(dto.getTelefono())) {
            throw new DuplicateResourceException("Ya existe un trabajador con el teléfono: " + dto.getTelefono());
        }
        if (trabajadorRepository.existsByDni(dto.getDni())) {
            throw new DuplicateResourceException("Ya existe un trabajador con el DNI: " + dto.getDni());
        }
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", dto.getCategoriaId()));

        Trabajador trabajador = new Trabajador();
        trabajador.setNombre(dto.getNombre());
        trabajador.setApellido(dto.getApellido());
        trabajador.setTelefono(dto.getTelefono());
        trabajador.setPassword(passwordEncoder.encode(dto.getPassword()));
        trabajador.setDni(dto.getDni());
        trabajador.setFotoPerfil(dto.getFotoPerfil());
        trabajador.setCiudad(dto.getCiudad());
        trabajador.setPerfilVerificado(false);
        trabajador.setCategoria(categoria);
        Trabajador saved = trabajadorRepository.save(trabajador);

        CustomUserDetails userDetails = new CustomUserDetails(
                saved.getId(), saved.getTelefono(), saved.getPassword(), Role.TRABAJADOR
        );
        return new AuthResponseDto(jwtService.generateToken(userDetails), Role.TRABAJADOR.name(), saved.getId());
    }

}