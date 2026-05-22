package com.puntooficio.puntooficio.trabajador.dtos.request;

import com.puntooficio.puntooficio.trabajador.models.Trabajador.MetodoContacto;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

// DTO usado por el trabajador autenticado para editar su propio panel.
// Todos los campos son opcionales: solo se aplican los que vienen != null (patch parcial).
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TrabajadorPanelUpdateRequestDto {

    private Long ciudadId;

    private String fotoPerfil;

    @Size(max = 500)
    private String biografia;

    private String whatsappContacto;

    private String instagramUsuario;

    private MetodoContacto metodoContacto;

    private Set<Long> subcategoriaIds;

    private Long categoriaId;
}
