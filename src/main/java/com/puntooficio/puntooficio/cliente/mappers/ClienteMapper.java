package com.puntooficio.puntooficio.cliente.mappers;

import com.puntooficio.puntooficio.cliente.dtos.request.ClienteRequestDto;
import com.puntooficio.puntooficio.cliente.dtos.response.ClienteResponseDto;
import com.puntooficio.puntooficio.cliente.models.Cliente;

import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequestDto dto) {
        if (dto == null) return null;
        return Cliente.builder()
                .nombreCompleto(dto.getNombreCompleto())
                .telefono(dto.getTelefono())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .build();
    }

    public ClienteResponseDto toDto(Cliente entity) {
        if (entity == null) return null;
        return ClienteResponseDto.builder()
                .id(entity.getId())
                .nombreCompleto(entity.getNombreCompleto())
                .telefono(entity.getTelefono())
                .email(entity.getEmail())
                .build();
    }

    public void updateEntityFromDto(ClienteRequestDto dto, Cliente entity) {
        if (dto == null || entity == null) return;
        entity.setNombreCompleto(dto.getNombreCompleto());
        entity.setTelefono(dto.getTelefono());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
    }
}