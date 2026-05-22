package com.puntooficio.puntooficio.subcategoria.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SubcategoriaRequestDto {

    @NotBlank
    private String nombre;

    @NotNull
    private Long categoriaId;
}
