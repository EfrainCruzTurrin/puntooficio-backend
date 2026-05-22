package com.puntooficio.puntooficio.galeria.dtos.response;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GaleriaImagenResponseDto {
    private Long id;
    private String url;
}
