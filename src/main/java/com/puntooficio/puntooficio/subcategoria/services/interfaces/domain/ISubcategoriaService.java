package com.puntooficio.puntooficio.subcategoria.services.interfaces.domain;

import com.puntooficio.puntooficio.subcategoria.dtos.response.SubcategoriaResponseDto;

import java.util.List;

public interface ISubcategoriaService {
    List<SubcategoriaResponseDto> findByCategoriaId(Long categoriaId);
    List<SubcategoriaResponseDto> findAll();
}
