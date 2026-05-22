package com.puntooficio.puntooficio.categoria.dtos.response;

import com.puntooficio.puntooficio.subcategoria.dtos.response.SubcategoriaResponseDto;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CategoriaConSubcategoriasResponseDto {
    @EqualsAndHashCode.Include
    private Long id;
    private String nombre;
    private String descripcion;
    private List<SubcategoriaResponseDto> subcategorias;
}