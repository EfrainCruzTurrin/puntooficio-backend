// com/puntooficio/puntooficio/mappers/ResenaMapper.java
package com.puntooficio.puntooficio.resena.mappers;

import com.puntooficio.puntooficio.categoria.dtos.response.CategoriaResponseDto;
import com.puntooficio.puntooficio.categoria.models.Categoria;
import com.puntooficio.puntooficio.resena.dtos.request.ResenaRequestDto;
import com.puntooficio.puntooficio.resena.dtos.response.ResenaResponseDto;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorResponseDto;
import com.puntooficio.puntooficio.cliente.dtos.response.ClienteResponseDto;
import com.puntooficio.puntooficio.resena.models.Resena;
import com.puntooficio.puntooficio.trabajador.models.Trabajador;
import com.puntooficio.puntooficio.cliente.models.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ResenaMapper {

    public Resena toEntity(ResenaRequestDto dto, Trabajador trabajador, Cliente cliente) {
        if (dto == null) return null;
        return Resena.builder()
                .puntaje(dto.getPuntaje())
                .comentario(dto.getComentario())
                .fechaCreacion(dto.getFechaCreacion())
                .trabajador(trabajador)
                .cliente(cliente)
                .build();
    }

    public ResenaResponseDto toResponseDto(Resena resena) {
        if (resena == null) return null;
        return ResenaResponseDto.builder()
                .id(resena.getId())
                .puntaje(resena.getPuntaje())
                .comentario(resena.getComentario())
                .fechaCreacion(resena.getFechaCreacion())
                .trabajador(toTrabajadorResponseDto(resena.getTrabajador()))
                .cliente(toClienteResponseDto(resena.getCliente()))
                .build();
    }

    public void updateEntityFromDto(ResenaRequestDto dto, Resena resena, Trabajador trabajador, Cliente cliente) {
        if (dto == null || resena == null) return;
        resena.setPuntaje(dto.getPuntaje());
        resena.setComentario(dto.getComentario());
        resena.setFechaCreacion(dto.getFechaCreacion());
        resena.setTrabajador(trabajador);
        resena.setCliente(cliente);
    }

    public void partialUpdateEntityFromDto(ResenaRequestDto dto, Resena resena, Trabajador trabajador, Cliente cliente) {
        if (dto == null || resena == null) return;
        if (dto.getPuntaje() != null) resena.setPuntaje(dto.getPuntaje());
        if (dto.getComentario() != null) resena.setComentario(dto.getComentario());
        if (dto.getFechaCreacion() != null) resena.setFechaCreacion(dto.getFechaCreacion());
        if (trabajador != null) resena.setTrabajador(trabajador);
        if (cliente != null) resena.setCliente(cliente);
    }

    private TrabajadorResponseDto toTrabajadorResponseDto(Trabajador t) {
        if (t == null) return null;
        TrabajadorResponseDto dto = new TrabajadorResponseDto();
        dto.setId(t.getId());
        dto.setNombre(t.getNombre());
        dto.setApellido(t.getApellido());
        dto.setTelefono(t.getTelefono());
        dto.setCategoria(toCategoriaResponseDto(t.getCategoria()));
        dto.setCiudad(t.getCiudad());
        dto.setPerfilVerificado(t.getPerfilVerificado());
        dto.setFotoPerfil(t.getFotoPerfil());
        return dto;
    }

    private CategoriaResponseDto toCategoriaResponseDto(Categoria categoria) {
        if (categoria == null) return null;
        CategoriaResponseDto dto = new CategoriaResponseDto();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        return dto;
    }

    private ClienteResponseDto toClienteResponseDto(Cliente c) {
        if (c == null) return null;
        ClienteResponseDto dto = new ClienteResponseDto();
        dto.setId(c.getId());
        dto.setNombreCompleto(c.getNombreCompleto());
        dto.setTelefono(c.getTelefono());
        dto.setEmail(c.getEmail());
        return dto;
    }
}