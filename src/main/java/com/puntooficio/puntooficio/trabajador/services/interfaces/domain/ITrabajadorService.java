package com.puntooficio.puntooficio.trabajador.services.interfaces.domain;

import com.puntooficio.puntooficio.shared.interfaces.ICrudService;
import com.puntooficio.puntooficio.trabajador.dtos.request.TrabajadorRequestDto;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITrabajadorService extends ICrudService<TrabajadorResponseDto, TrabajadorRequestDto, Long> {
    Page<TrabajadorResponseDto> search(Long categoriaId, String ciudad, Pageable pageable);
}