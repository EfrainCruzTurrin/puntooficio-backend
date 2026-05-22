package com.puntooficio.puntooficio.trabajador.services.interfaces.domain;

import com.puntooficio.puntooficio.galeria.dtos.response.GaleriaImagenResponseDto;
import com.puntooficio.puntooficio.shared.interfaces.ICrudService;
import com.puntooficio.puntooficio.trabajador.dtos.request.TrabajadorPanelUpdateRequestDto;
import com.puntooficio.puntooficio.trabajador.dtos.request.TrabajadorRequestDto;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorListaResponseDto;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorPerfilResponseDto;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITrabajadorService extends ICrudService<TrabajadorResponseDto, TrabajadorRequestDto, Long> {

    Page<TrabajadorResponseDto> search(Long categoriaId, String ciudad, Pageable pageable);

    // Panel privado: el trabajador autenticado consulta su propio perfil
    TrabajadorPerfilResponseDto getPanel(Long trabajadorId);

    // Panel privado: el trabajador autenticado actualiza su propio panel
    TrabajadorPerfilResponseDto updatePanel(Long trabajadorId, TrabajadorPanelUpdateRequestDto dto);

    // Perfil público
    TrabajadorPerfilResponseDto getPerfilPublico(Long trabajadorId);

    // Lista resumida para cards/listados
    Page<TrabajadorListaResponseDto> getLista(Long categoriaId, String ciudad, Pageable pageable);

    GaleriaImagenResponseDto agregarImagenGaleria(Long trabajadorId, String url);
    void eliminarImagenGaleria(Long trabajadorId, Long imagenId);
}
