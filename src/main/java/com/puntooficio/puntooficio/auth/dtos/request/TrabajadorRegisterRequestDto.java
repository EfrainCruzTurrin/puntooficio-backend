package com.puntooficio.puntooficio.auth.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class TrabajadorRegisterRequestDto {
    private String nombre;
    private String apellido;
    private String telefono;
    private String password;
    private String dni;
    private String fotoPerfil;
    private Long ciudadId;
    private Long categoriaId;
    private String email;
    private Set<Long> subcategoriaIds;
}