package com.puntooficio.puntooficio.trabajador.dtos.response;

import com.puntooficio.puntooficio.categoria.dtos.response.CategoriaResponseDto;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TrabajadorResponseDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String telefono;
    private CategoriaResponseDto categoria;
    private String ciudad;
    private Boolean perfilVerificado;
    private String fotoPerfil;
}
