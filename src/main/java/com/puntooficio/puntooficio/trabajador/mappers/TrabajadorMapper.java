package com.puntooficio.puntooficio.trabajador.mappers;

import com.puntooficio.puntooficio.categoria.mappers.CategoriaMapper;
import com.puntooficio.puntooficio.trabajador.dtos.request.TrabajadorRequestDto;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorResponseDto;
import com.puntooficio.puntooficio.trabajador.models.Trabajador;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class TrabajadorMapper {

    private final CategoriaMapper categoriaMapper;

    public TrabajadorResponseDto toResponseDto(Trabajador trabajador){
        return TrabajadorResponseDto.builder()
                .id(trabajador.getId())
                .nombre(trabajador.getNombre())
                .apellido(trabajador.getApellido())
                .categoria(categoriaMapper.toResponseDto(trabajador.getCategoria()))
                .telefono(trabajador.getTelefono())
                .ciudad(trabajador.getCiudad())
                .fotoPerfil(trabajador.getFotoPerfil())
                .perfilVerificado(trabajador.getPerfilVerificado())
                .build();
    }

    public Trabajador toEntity(TrabajadorRequestDto dto){
        return Trabajador.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .telefono(dto.getTelefono())
                .password(dto.getPassword())
                .dni(dto.getDni())
                .fotoPerfil(dto.getFotoPerfil())
                .ciudad(dto.getCiudad())
                .perfilVerificado(false)
                .build();




    }

}
