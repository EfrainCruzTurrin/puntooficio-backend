package com.puntooficio.puntooficio.trabajador.dtos.response;

import com.puntooficio.puntooficio.categoria.dtos.response.CategoriaResponseDto;
import com.puntooficio.puntooficio.galeria.dtos.response.GaleriaImagenResponseDto;
import com.puntooficio.puntooficio.subcategoria.dtos.response.SubcategoriaResponseDto;
import com.puntooficio.puntooficio.trabajador.models.Trabajador.MetodoContacto;
import lombok.*;

import java.util.List;
import java.util.Set;

// Vista pública del perfil del trabajador (no incluye datos sensibles como dni, password, email).
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TrabajadorPerfilResponseDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String ciudad;
    private String fotoPerfil;
    private String biografia;
    private String whatsappContacto;
    private String instagramUsuario;
    private MetodoContacto metodoContacto;
    private Boolean perfilVerificado;
    private Set<CategoriaResponseDto> categorias; // ← plural, era singular
    private Set<SubcategoriaResponseDto> subcategorias;
    private List<GaleriaImagenResponseDto> galeria;
}
