package com.puntooficio.puntooficio.subcategoria.services.impl.domain;

import com.puntooficio.puntooficio.subcategoria.dtos.response.SubcategoriaResponseDto;
import com.puntooficio.puntooficio.subcategoria.mappers.SubcategoriaMapper;
import com.puntooficio.puntooficio.subcategoria.repositories.SubcategoriaRepository;
import com.puntooficio.puntooficio.subcategoria.services.interfaces.domain.ISubcategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubcategoriaServiceImpl implements ISubcategoriaService {

    private final SubcategoriaRepository subcategoriaRepository;
    private final SubcategoriaMapper subcategoriaMapper;

    @Override
    public List<SubcategoriaResponseDto> findByCategoriaId(Long categoriaId) {
        return subcategoriaRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(subcategoriaMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<SubcategoriaResponseDto> findAll() {
        return subcategoriaRepository.findAll()
                .stream()
                .map(subcategoriaMapper::toResponseDto)
                .toList();
    }
}
