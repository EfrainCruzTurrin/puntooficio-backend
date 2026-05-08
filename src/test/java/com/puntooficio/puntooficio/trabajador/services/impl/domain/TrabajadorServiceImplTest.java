package com.puntooficio.puntooficio.trabajador.services.impl.domain;

import com.puntooficio.puntooficio.categoria.dtos.response.CategoriaResponseDto;
import com.puntooficio.puntooficio.categoria.models.Categoria;
import com.puntooficio.puntooficio.categoria.repositories.CategoriaRepository;
import com.puntooficio.puntooficio.trabajador.dtos.request.TrabajadorRequestDto;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorResponseDto;
import com.puntooficio.puntooficio.trabajador.mappers.TrabajadorMapper;
import com.puntooficio.puntooficio.trabajador.models.Trabajador;
import com.puntooficio.puntooficio.trabajador.repositories.TrabajadorRepository;
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
class TrabajadorServiceImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private TrabajadorRepository trabajadorRepository;

    @Mock
    private TrabajadorMapper trabajadorMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private TrabajadorServiceImpl trabajadorService;

    private Trabajador trabajador;
    private TrabajadorResponseDto responseDto;
    private TrabajadorRequestDto requestDto;

    @BeforeEach
    void setUp() {
        trabajador = new Trabajador();
        trabajador.setId(1L);
        trabajador.setNombre("Juan");
        trabajador.setApellido("Pérez");
        trabajador.setTelefono("3511234567");
        trabajador.setCiudad("Córdoba");

        CategoriaResponseDto categoriaDto = new CategoriaResponseDto(1L, "Carpintería", "Servicios de madera");
        responseDto = new TrabajadorResponseDto(1L, "Juan", "Pérez", "3511234567", categoriaDto, "Córdoba", false, null);
        requestDto = new TrabajadorRequestDto("Juan", "Pérez", "3511234567", "password123", "12345678", null, "Córdoba", 1L);
    }

    @Test
    void findAll_debeRetornarPaginaDeTrabajadoresDto() {
        Page<Trabajador> page = new PageImpl<>(List.of(trabajador));
        when(trabajadorRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(trabajadorMapper.toResponseDto(trabajador)).thenReturn(responseDto);

        Page<TrabajadorResponseDto> result = trabajadorService.findAll(PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getNombre()).isEqualTo("Juan");
    }

    @Test
    void findById_cuandoExiste_debeRetornarDto() {
        when(trabajadorRepository.findById(1L)).thenReturn(Optional.of(trabajador));
        when(trabajadorMapper.toResponseDto(trabajador)).thenReturn(responseDto);

        TrabajadorResponseDto result = trabajadorService.findById(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void findById_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        when(trabajadorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> trabajadorService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_debeGuardarYRetornarDto() {
        when(passwordEncoder.encode(any())).thenReturn("hashedPassword");
        when(trabajadorMapper.toEntity(requestDto)).thenReturn(trabajador);
        when(trabajadorRepository.save(trabajador)).thenReturn(trabajador);
        when(trabajadorMapper.toResponseDto(trabajador)).thenReturn(responseDto);
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(new Categoria()));

        TrabajadorResponseDto result = trabajadorService.create(requestDto);

        assertThat(result.getNombre()).isEqualTo("Juan");
        verify(trabajadorRepository).save(trabajador);
    }

    @Test
    void delete_cuandoExiste_debeEliminar() {
        when(trabajadorRepository.findById(1L)).thenReturn(Optional.of(trabajador));

        trabajadorService.delete(1L);

        verify(trabajadorRepository).delete(trabajador);
    }

    @Test
    void delete_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        when(trabajadorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> trabajadorService.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}