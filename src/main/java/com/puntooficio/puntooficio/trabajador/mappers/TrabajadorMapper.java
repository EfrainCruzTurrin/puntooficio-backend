package com.puntooficio.puntooficio.trabajador.mappers;

import com.puntooficio.puntooficio.categoria.dtos.response.CategoriaResponseDto;
import com.puntooficio.puntooficio.categoria.mappers.CategoriaMapper;
import com.puntooficio.puntooficio.ciudad.models.Ciudad;
import com.puntooficio.puntooficio.galeria.dtos.response.GaleriaImagenResponseDto;
import com.puntooficio.puntooficio.galeria.mappers.GaleriaImagenMapper;
import com.puntooficio.puntooficio.subcategoria.dtos.response.SubcategoriaResponseDto;
import com.puntooficio.puntooficio.subcategoria.mappers.SubcategoriaMapper;
import com.puntooficio.puntooficio.trabajador.dtos.request.TrabajadorRequestDto;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorListaResponseDto;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorPerfilResponseDto;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorResponseDto;
import com.puntooficio.puntooficio.trabajador.models.Trabajador;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TrabajadorMapper {

    private final CategoriaMapper categoriaMapper;
    private final SubcategoriaMapper subcategoriaMapper;
    private final GaleriaImagenMapper galeriaImagenMapper;

    public TrabajadorResponseDto toResponseDto(Trabajador trabajador) {
        return TrabajadorResponseDto.builder()
                .id(trabajador.getId())
                .nombre(trabajador.getNombre())
                .apellido(trabajador.getApellido())
                .telefono(trabajador.getTelefono())
                .ciudad(trabajador.getCiudad() != null ? trabajador.getCiudad().getNombre() : null)
                .fotoPerfil(trabajador.getFotoPerfil())
                .perfilVerificado(trabajador.getPerfilVerificado())
                .build();
    }
    public Trabajador toEntity(TrabajadorRequestDto dto, Ciudad ciudad) {
        return Trabajador.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .dni(dto.getDni())
                .telefono(dto.getTelefono())
                .password(dto.getPassword())
                .fotoPerfil(dto.getFotoPerfil())
                .ciudad(ciudad)
                .perfilVerificado(false)
                .build();
    }

    public TrabajadorPerfilResponseDto toPerfilDto(Trabajador trabajador) {
        Set<SubcategoriaResponseDto> subs = trabajador.getSubcategorias() == null
                ? Set.of()
                : trabajador.getSubcategorias().stream()
                  .map(subcategoriaMapper::toResponseDto)
                  .collect(Collectors.toSet());

        // ✅ Categorías derivadas automáticamente de las subcategorías
        Set<CategoriaResponseDto> categoriasDerivadas = trabajador.getSubcategorias() == null
                ? Set.of()
                : trabajador.getSubcategorias().stream()
                  .map(sub -> categoriaMapper.toResponseDto(sub.getCategoria()))
                  .collect(Collectors.toSet()); // Set elimina duplicados automáticamente

        List<GaleriaImagenResponseDto> galeria = trabajador.getGaleria() == null
                ? List.of()
                : trabajador.getGaleria().stream()
                  .map(galeriaImagenMapper::toResponseDto)
                  .toList();

        return TrabajadorPerfilResponseDto.builder()
                .id(trabajador.getId())
                .nombre(trabajador.getNombre())
                .apellido(trabajador.getApellido())
                .ciudad(trabajador.getCiudad() != null ? trabajador.getCiudad().getNombre() : null)
                .fotoPerfil(trabajador.getFotoPerfil())
                .biografia(trabajador.getBiografia())
                .whatsappContacto(trabajador.getWhatsappContacto())
                .instagramUsuario(trabajador.getInstagramUsuario())
                .metodoContacto(trabajador.getMetodoContacto())
                .perfilVerificado(trabajador.getPerfilVerificado())
                // ❌ Eliminar: .categoria(trabajador.getCategoria() != null ? ... : null)
                .categorias(categoriasDerivadas)
                .subcategorias(subs)
                .galeria(galeria)
                .build();
    }

    public TrabajadorListaResponseDto toListaDto(Trabajador trabajador) {
        Set<com.puntooficio.puntooficio.subcategoria.dtos.response.SubcategoriaResponseDto> subs =
                trabajador.getSubcategorias() == null
                        ? Set.of()
                        : trabajador.getSubcategorias().stream()
                          .map(subcategoriaMapper::toResponseDto)
                          .collect(Collectors.toSet());

        int cantidadResenas = trabajador.getResenas() == null ? 0 : trabajador.getResenas().size();
        Double puntajePromedio = cantidadResenas == 0 ? null :
                trabajador.getResenas().stream()
                .mapToInt(r -> r.getPuntaje())
                .average()
                .orElse(0);

        return TrabajadorListaResponseDto.builder()
                .id(trabajador.getId())
                .nombre(trabajador.getNombre())
                .apellido(trabajador.getApellido())
                .ciudad(trabajador.getCiudad() != null ? trabajador.getCiudad().getNombre() : null)
                .fotoPerfil(trabajador.getFotoPerfil())
                .perfilVerificado(trabajador.getPerfilVerificado())
                .subcategorias(subs)
                .puntajePromedio(puntajePromedio)
                .cantidadResenas(cantidadResenas)
                .build();
    }
}
