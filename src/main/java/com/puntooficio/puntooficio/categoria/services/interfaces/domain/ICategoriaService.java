// services/interfaces/domain/ICategoriaService.java
package com.puntooficio.puntooficio.categoria.services.interfaces.domain;

import com.puntooficio.puntooficio.categoria.dtos.request.CategoriaRequestDto;
import com.puntooficio.puntooficio.categoria.dtos.response.CategoriaResponseDto;
import com.puntooficio.puntooficio.shared.interfaces.ICrudService;

public interface ICategoriaService
        extends ICrudService<CategoriaResponseDto, CategoriaRequestDto, Long> {
    // Métodos específicos de Categoria si los necesitás en el futuro
}