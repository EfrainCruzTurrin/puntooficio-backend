package com.puntooficio.puntooficio.cliente.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRequestDto {

    @NotBlank
    private String nombreCompleto;
    @NotBlank
    private String telefono;
    @NotBlank
    @Size(min=8)
    private String password;
    @NotBlank @Email
    private String email;
}