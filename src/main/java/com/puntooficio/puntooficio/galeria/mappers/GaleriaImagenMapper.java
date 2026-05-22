package com.puntooficio.puntooficio.galeria.mappers;

import com.puntooficio.puntooficio.galeria.dtos.response.GaleriaImagenResponseDto;
import com.puntooficio.puntooficio.galeria.models.GaleriaImagen;
import org.springframework.stereotype.Component;

@Component
public class GaleriaImagenMapper {

    public GaleriaImagenResponseDto toResponseDto(GaleriaImagen imagen) {
        return GaleriaImagenResponseDto.builder()
                .id(imagen.getId())
                .url(imagen.getUrl())
                .build();
    }
}
