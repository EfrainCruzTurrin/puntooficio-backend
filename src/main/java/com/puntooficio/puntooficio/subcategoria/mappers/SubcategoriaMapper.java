package com.puntooficio.puntooficio.subcategoria.mappers;

import com.puntooficio.puntooficio.subcategoria.dtos.response.SubcategoriaResponseDto;
import com.puntooficio.puntooficio.subcategoria.models.Subcategoria;
import org.springframework.stereotype.Component;

@Component
public class SubcategoriaMapper {

    public SubcategoriaResponseDto toResponseDto(Subcategoria subcategoria) {
        return SubcategoriaResponseDto.builder()
                .id(subcategoria.getId())
                .nombre(subcategoria.getNombre())
                .categoriaId(subcategoria.getCategoria() != null ? subcategoria.getCategoria().getId() : null)
                .categoriaId(subcategoria.getCategoria() != null ? subcategoria.getCategoria().getId() : null)
                .categoriaNombre(subcategoria.getCategoria() != null ? subcategoria.getCategoria().getNombre() : null)
                .build();

    }
}
