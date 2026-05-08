package com.puntooficio.puntooficio.categoria.mappers;

import com.puntooficio.puntooficio.categoria.dtos.request.CategoriaRequestDto;
import com.puntooficio.puntooficio.categoria.dtos.response.CategoriaResponseDto;
import com.puntooficio.puntooficio.categoria.models.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public CategoriaResponseDto toResponseDto(Categoria categoria) {
        return CategoriaResponseDto.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .build();
    }

    public Categoria toEntity(CategoriaRequestDto dto) {
        return Categoria.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .build();
    }
}