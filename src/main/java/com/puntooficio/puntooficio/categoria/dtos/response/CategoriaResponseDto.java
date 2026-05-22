package com.puntooficio.puntooficio.categoria.dtos.response;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CategoriaResponseDto {
    @EqualsAndHashCode.Include
    private Long id;
    private String nombre;
    private String descripcion;
}