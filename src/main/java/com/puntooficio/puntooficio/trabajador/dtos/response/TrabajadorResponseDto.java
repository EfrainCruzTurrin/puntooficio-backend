package com.puntooficio.puntooficio.trabajador.dtos.response;

import com.puntooficio.puntooficio.categoria.dtos.response.CategoriaResponseDto;
import lombok.*;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TrabajadorResponseDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String telefono;
    private Set<CategoriaResponseDto> categorias;
    private String ciudad;
    private Boolean perfilVerificado;
    private String fotoPerfil;
}
