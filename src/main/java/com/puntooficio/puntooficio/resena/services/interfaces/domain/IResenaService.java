package com.puntooficio.puntooficio.resena.services.interfaces.domain;

import com.puntooficio.puntooficio.resena.dtos.request.ResenaRequestDto;
import com.puntooficio.puntooficio.resena.dtos.response.ResenaResponseDto;
import com.puntooficio.puntooficio.shared.interfaces.ICrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IResenaService extends ICrudService<ResenaResponseDto, ResenaRequestDto, Long> {
    Page<ResenaResponseDto> findByTrabajadorId(Long trabajadorId, Pageable pageable);
}