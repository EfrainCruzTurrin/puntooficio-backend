package com.puntooficio.puntooficio.cliente.dtos.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponseDto {

    private Long id;
    private String nombreCompleto;
    private String telefono;
    private String email;
}