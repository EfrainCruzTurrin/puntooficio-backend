package com.puntooficio.puntooficio.auth.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRegisterRequestDto {
    private String nombreCompleto;
    private String telefono;
    private String password;
    private String email;
}