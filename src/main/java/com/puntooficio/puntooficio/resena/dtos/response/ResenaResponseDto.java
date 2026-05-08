package com.puntooficio.puntooficio.resena.dtos.response;

import com.puntooficio.puntooficio.cliente.dtos.response.ClienteResponseDto;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorResponseDto;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ResenaResponseDto {

    private Long id;
    private Integer puntaje;
    private String comentario;
    private LocalDateTime fechaCreacion;
    private TrabajadorResponseDto trabajador;
    private ClienteResponseDto cliente;
}