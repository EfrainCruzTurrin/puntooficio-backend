package com.puntooficio.puntooficio.trabajador.dtos.response;

import com.puntooficio.puntooficio.subcategoria.dtos.response.SubcategoriaResponseDto;
import lombok.*;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TrabajadorListaResponseDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String ciudad;
    private String fotoPerfil;
    private Boolean perfilVerificado;
    private Set<SubcategoriaResponseDto> subcategorias;
    private Double puntajePromedio;
    private Integer cantidadResenas;
}