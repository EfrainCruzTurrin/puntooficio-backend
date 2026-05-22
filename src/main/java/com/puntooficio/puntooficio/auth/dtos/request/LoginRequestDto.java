package com.puntooficio.puntooficio.auth.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String identifier; // email para Cliente, telefono para Trabajador
    private String email;
    private String password;
}