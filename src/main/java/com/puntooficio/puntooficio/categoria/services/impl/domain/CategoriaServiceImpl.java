package com.puntooficio.puntooficio.categoria.services.impl.domain;

import com.puntooficio.puntooficio.categoria.dtos.request.CategoriaRequestDto;
import com.puntooficio.puntooficio.categoria.dtos.response.CategoriaResponseDto;
import com.puntooficio.puntooficio.categoria.mappers.CategoriaMapper;
import com.puntooficio.puntooficio.categoria.models.Categoria;
import com.puntooficio.puntooficio.categoria.repositories.CategoriaRepository;
import com.puntooficio.puntooficio.categoria.services.interfaces.domain.ICategoriaService;
import com.puntooficio.puntooficio.shared.exceptions.DuplicateResourceException;
import com.puntooficio.puntooficio.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements ICategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;


    @Override
    public CategoriaResponseDto findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", id));
        return categoriaMapper.toResponseDto(categoria);
    }

    @Override
    public CategoriaResponseDto create(CategoriaRequestDto dto) {
        if (categoriaRepository.existsByNombre(dto.getNombre())) {
            throw new DuplicateResourceException("Ya existe una categoria con ese nombre");
        }
        Categoria nueva = categoriaMapper.toEntity(dto);
        return categoriaMapper.toResponseDto(categoriaRepository.save(nueva));
    }

    @Override
    public CategoriaResponseDto update(Long id, CategoriaRequestDto dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", id));
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        return categoriaMapper.toResponseDto(categoriaRepository.save(categoria));
    }

    @Override
    public CategoriaResponseDto partialUpdate(Long id, CategoriaRequestDto dto) {
        Categoria existing = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", id));
        if (dto.getNombre() != null) existing.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) existing.setDescripcion(dto.getDescripcion());
        return categoriaMapper.toResponseDto(categoriaRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", id));
        categoriaRepository.delete(categoria);
    }
    @Override
    public Page<CategoriaResponseDto> findAll(Pageable pageable) {
        return categoriaRepository.findAll(pageable).map(categoriaMapper::toResponseDto);
    }
}