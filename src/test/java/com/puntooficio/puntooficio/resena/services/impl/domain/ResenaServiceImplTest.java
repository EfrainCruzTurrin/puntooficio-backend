package com.puntooficio.puntooficio.resena.services.impl.domain;

import com.puntooficio.puntooficio.categoria.dtos.response.CategoriaResponseDto;
import com.puntooficio.puntooficio.cliente.dtos.response.ClienteResponseDto;
import com.puntooficio.puntooficio.cliente.models.Cliente;
import com.puntooficio.puntooficio.cliente.repositories.ClienteRepository;
import com.puntooficio.puntooficio.resena.dtos.request.ResenaRequestDto;
import com.puntooficio.puntooficio.resena.dtos.response.ResenaResponseDto;
import com.puntooficio.puntooficio.resena.mappers.ResenaMapper;
import com.puntooficio.puntooficio.resena.models.Resena;
import com.puntooficio.puntooficio.resena.repositories.ResenaRepository;
import com.puntooficio.puntooficio.shared.exceptions.ResourceNotFoundException;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorResponseDto;
import com.puntooficio.puntooficio.trabajador.models.Trabajador;
import com.puntooficio.puntooficio.trabajador.repositories.TrabajadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResenaServiceImplTest {

    @Mock
    private ResenaRepository resenaRepository;

    @Mock
    private TrabajadorRepository trabajadorRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ResenaMapper resenaMapper;

    @InjectMocks
    private ResenaServiceImpl resenaService;

    private Resena resena;
    private Trabajador trabajador;
    private Cliente cliente;
    private ResenaResponseDto responseDto;
    private ResenaRequestDto requestDto;

    @BeforeEach
    void setUp() {
        trabajador = new Trabajador();
        trabajador.setId(1L);

        cliente = new Cliente();
        cliente.setId(1L);

        resena = new Resena();
        resena.setId(1L);
        resena.setPuntaje(5);
        resena.setComentario("Excelente trabajo");
        resena.setFechaCreacion(LocalDateTime.now());

        CategoriaResponseDto categoriaDto = new CategoriaResponseDto(1L, "Carpintería", "Servicios de madera");
        TrabajadorResponseDto trabajadorDto = new TrabajadorResponseDto(1L, "Juan", "Pérez", "3511234567", categoriaDto, "Córdoba", false, null);
        ClienteResponseDto clienteDto = new ClienteResponseDto(1L, "Efrain Turrin", "3533123456", "efrain@example.com");

        responseDto = new ResenaResponseDto(1L, 5, "Excelente trabajo", LocalDateTime.now(), trabajadorDto, clienteDto);
        requestDto = new ResenaRequestDto(5, "Excelente trabajo", LocalDateTime.now(), 1L, 1L);
    }

    @Test
    void findAll_debeRetornarPaginaDeResenasDto() {
        Page<Resena> page = new PageImpl<>(List.of(resena));
        when(resenaRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(resenaMapper.toResponseDto(resena)).thenReturn(responseDto);

        Page<ResenaResponseDto> result = resenaService.findAll(PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getPuntaje()).isEqualTo(5);
    }

    @Test
    void findById_cuandoExiste_debeRetornarDto() {
        when(resenaRepository.findById(1L)).thenReturn(Optional.of(resena));
        when(resenaMapper.toResponseDto(resena)).thenReturn(responseDto);

        ResenaResponseDto result = resenaService.findById(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void findById_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        when(resenaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> resenaService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_debeGuardarYRetornarDto() {
        when(trabajadorRepository.findById(1L)).thenReturn(Optional.of(trabajador));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(resenaMapper.toEntity(requestDto, trabajador, cliente)).thenReturn(resena);
        when(resenaRepository.save(resena)).thenReturn(resena);
        when(resenaMapper.toResponseDto(resena)).thenReturn(responseDto);

        ResenaResponseDto result = resenaService.create(requestDto);

        assertThat(result.getPuntaje()).isEqualTo(5);
        verify(resenaRepository).save(resena);
    }

    @Test
    void delete_cuandoExiste_debeEliminar() {
        when(resenaRepository.findById(1L)).thenReturn(Optional.of(resena));

        resenaService.delete(1L);

        verify(resenaRepository).delete(resena);
    }

    @Test
    void delete_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        when(resenaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> resenaService.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}