package com.puntooficio.puntooficio.resena.dtos.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ResenaRequestDto {

    @NotNull @Min(1) @Max(5)
    private Integer puntaje;
    private String comentario;
    private LocalDateTime fechaCreacion;
    @NotNull
    private Long trabajadorId;
    @NotNull
    private Long clienteId;
}