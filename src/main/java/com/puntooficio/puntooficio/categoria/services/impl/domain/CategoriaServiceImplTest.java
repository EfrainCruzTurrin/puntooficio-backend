package com.puntooficio.puntooficio.categoria.services.impl.domain;

import com.puntooficio.puntooficio.categoria.dtos.request.CategoriaRequestDto;
import com.puntooficio.puntooficio.categoria.dtos.response.CategoriaResponseDto;
import com.puntooficio.puntooficio.categoria.mappers.CategoriaMapper;
import com.puntooficio.puntooficio.categoria.models.Categoria;
import com.puntooficio.puntooficio.categoria.repositories.CategoriaRepository;
import org.springframework.data.domain.PageImpl;
import com.puntooficio.puntooficio.shared.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaMapper categoriaMapper;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    private Categoria categoria;
    private CategoriaResponseDto responseDto;
    private CategoriaRequestDto requestDto;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Carpintería");
        categoria.setDescripcion("Servicios de madera");

        responseDto = new CategoriaResponseDto(1L, "Carpintería", "Servicios de madera");
        requestDto = new CategoriaRequestDto("Carpintería", "Servicios de madera");
    }

    @Test
    void findAll_debeRetornarPaginaDeCategoriasDto() {
        Page<Categoria> page = new PageImpl<>(List.of(categoria));
        when(categoriaRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(categoriaMapper.toResponseDto(categoria)).thenReturn(responseDto);

        Page<CategoriaResponseDto> result = categoriaService.findAll(PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getNombre()).isEqualTo("Carpintería");
    }

    @Test
    void findById_cuandoExiste_debeRetornarDto() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaMapper.toResponseDto(categoria)).thenReturn(responseDto);

        CategoriaResponseDto result = categoriaService.findById(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void findById_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoriaService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_debeGuardarYRetornarDto() {
        when(categoriaMapper.toEntity(requestDto)).thenReturn(categoria);
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        when(categoriaMapper.toResponseDto(categoria)).thenReturn(responseDto);

        CategoriaResponseDto result = categoriaService.create(requestDto);

        assertThat(result.getNombre()).isEqualTo("Carpintería");
        verify(categoriaRepository).save(categoria);
    }

    @Test
    void delete_cuandoExiste_debeEliminar() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        categoriaService.delete(1L);

        verify(categoriaRepository).delete(categoria);
    }

    @Test
    void delete_cuandoNoExiste_debeLanzarResourceNotFoundException() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoriaService.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}