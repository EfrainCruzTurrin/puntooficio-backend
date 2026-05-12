package com.puntooficio.puntooficio.auth.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrabajadorRegisterRequestDto {
    private String nombre;
    private String apellido;
    private String telefono;
    private String password;
    private String dni;
    private String fotoPerfil;
    private String ciudad;
    private Long categoriaId;
    private String email;
}