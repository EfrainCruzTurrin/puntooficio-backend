package com.puntooficio.puntooficio.trabajador.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrabajadorRequestDto {

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    private String telefono;

    @NotBlank @Size(min=8)
    private String password;

    @NotBlank
    private String dni;

    private String fotoPerfil;

    @NotBlank
    private String ciudad;

    @NotNull
    private Long categoriaId;
}