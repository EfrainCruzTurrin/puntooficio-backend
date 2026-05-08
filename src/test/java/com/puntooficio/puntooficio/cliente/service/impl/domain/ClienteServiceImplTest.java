package com.puntooficio.puntooficio.cliente.service.impl.domain;

import com.puntooficio.puntooficio.cliente.dtos.request.ClienteRequestDto;
import com.puntooficio.puntooficio.cliente.dtos.response.ClienteResponseDto;
import com.puntooficio.puntooficio.cliente.mappers.ClienteMapper;
import com.puntooficio.puntooficio.cliente.models.Cliente;
import com.puntooficio.puntooficio.cliente.repositories.ClienteRepository;
import com.puntooficio.puntooficio.shared.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;
    private ClienteResponseDto responseDto;
    private ClienteRequestDto requestDto;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombreCompleto("Efrain Turrin");
        cliente.setTelefono("3533123456");
        cliente.setEmail("efrain@example.com");

        responseDto = new ClienteResponseDto(1L, "Efrain Turrin", "3533123456", "efrain@example.com");
        requestDto = new ClienteRequestDto("Efrain Turrin", "3533123456", "password123", "efrain@example.com");
    }

    @Test
    void findAll_debeRetornarPaginaDeClientesDto() {
        Page<Cliente> page = new PageImpl<>(List.of(cliente));
        when(clienteRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(clienteMapper.toDto(cliente)).thenReturn(responseDto);

        Page<ClienteResponseDto> result = clienteService.findAll(PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getNombreCompleto()).isEqualTo("Efrain Turrin");
    }

    @Test
    void findById_cuandoExiste_debeRetornarDto() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toDto(cliente)).thenReturn(responseDto);

        ClienteResponseDto result = clienteService.findById(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void findById_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_debeGuardarYRetornarDto() {
        when(passwordEncoder.encode(any())).thenReturn("hashedPassword");
        when(clienteMapper.toEntity(requestDto)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(clienteMapper.toDto(cliente)).thenReturn(responseDto);

        ClienteResponseDto result = clienteService.create(requestDto);

        assertThat(result.getNombreCompleto()).isEqualTo("Efrain Turrin");
        verify(clienteRepository).save(cliente);
    }

    @Test
    void delete_cuandoExiste_debeEliminar() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        clienteService.delete(1L);

        verify(clienteRepository).delete(cliente);
    }

    @Test
    void delete_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}