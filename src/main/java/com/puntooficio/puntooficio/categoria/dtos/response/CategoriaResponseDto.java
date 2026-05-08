package com.puntooficio.puntooficio.categoria.dtos.response;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CategoriaResponseDto {
    private Long id;
    private String nombre;
    private String descripcion;
}
