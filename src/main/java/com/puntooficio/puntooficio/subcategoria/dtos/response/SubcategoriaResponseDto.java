package com.puntooficio.puntooficio.subcategoria.dtos.response;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SubcategoriaResponseDto {
    private Long id;
    private String nombre;
    private Long categoriaId;
    private String categoriaNombre;
}
