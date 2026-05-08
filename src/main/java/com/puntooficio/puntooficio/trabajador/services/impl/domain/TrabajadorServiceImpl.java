package com.puntooficio.puntooficio.trabajador.services.impl.domain;

import com.puntooficio.puntooficio.categoria.models.Categoria;
import com.puntooficio.puntooficio.categoria.repositories.CategoriaRepository;
import com.puntooficio.puntooficio.shared.exceptions.ResourceNotFoundException;
import com.puntooficio.puntooficio.trabajador.dtos.request.TrabajadorRequestDto;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorResponseDto;
import com.puntooficio.puntooficio.trabajador.mappers.TrabajadorMapper;
import com.puntooficio.puntooficio.trabajador.models.Trabajador;
import com.puntooficio.puntooficio.trabajador.repositories.TrabajadorRepository;
import com.puntooficio.puntooficio.trabajador.services.interfaces.domain.ITrabajadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrabajadorServiceImpl implements ITrabajadorService {

    private final TrabajadorRepository trabajadorRepository;
    private final CategoriaRepository categoriaRepository;
    private final TrabajadorMapper trabajadorMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TrabajadorResponseDto create(TrabajadorRequestDto dto) {
        Categoria categoria = findCategoriaOrThrow(dto.getCategoriaId());
        Trabajador trabajador = trabajadorMapper.toEntity(dto);
        trabajador.setPassword(passwordEncoder.encode(dto.getPassword()));
        trabajador.setCategoria(categoria);
        return trabajadorMapper.toResponseDto(trabajadorRepository.save(trabajador));
    }

    @Override
    public TrabajadorResponseDto findById(Long id) {
        return trabajadorMapper.toResponseDto(findOrThrow(id));
    }

    @Override
    public TrabajadorResponseDto update(Long id, TrabajadorRequestDto dto) {
        Trabajador existing = findOrThrow(id);
        Categoria categoria = findCategoriaOrThrow(dto.getCategoriaId());
        existing.setNombre(dto.getNombre());
        existing.setApellido(dto.getApellido());
        existing.setTelefono(dto.getTelefono());
        existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        existing.setDni(dto.getDni());
        existing.setFotoPerfil(dto.getFotoPerfil());
        existing.setCiudad(dto.getCiudad());
        existing.setCategoria(categoria);
        return trabajadorMapper.toResponseDto(trabajadorRepository.save(existing));
    }

    @Override
    public TrabajadorResponseDto partialUpdate(Long id, TrabajadorRequestDto dto) {
        Trabajador existing = findOrThrow(id);
        if (dto.getNombre() != null) existing.setNombre(dto.getNombre());
        if (dto.getApellido() != null) existing.setApellido(dto.getApellido());
        if (dto.getTelefono() != null) existing.setTelefono(dto.getTelefono());
        if (dto.getPassword() != null) existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (dto.getDni() != null) existing.setDni(dto.getDni());
        if (dto.getFotoPerfil() != null) existing.setFotoPerfil(dto.getFotoPerfil());
        if (dto.getCiudad() != null) existing.setCiudad(dto.getCiudad());
        if (dto.getCategoriaId() != null) existing.setCategoria(findCategoriaOrThrow(dto.getCategoriaId()));
        return trabajadorMapper.toResponseDto(trabajadorRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        Trabajador trabajador = findOrThrow(id);
        trabajadorRepository.delete(trabajador);
    }

    private Trabajador findOrThrow(Long id) {
        return trabajadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trabajador", id));
    }

    private Categoria findCategoriaOrThrow(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", id));
    }

    @Override
    public Page<TrabajadorResponseDto> findAll(Pageable pageable) {
        return trabajadorRepository.findAll(pageable).map(trabajadorMapper::toResponseDto);
    }

    @Override
    public Page<TrabajadorResponseDto> search(Long categoriaId, String ciudad, Pageable pageable) {
        if (categoriaId != null && ciudad != null) {
            return trabajadorRepository.findByCategoriaIdAndCiudad(categoriaId, ciudad, pageable)
                    .map(trabajadorMapper::toResponseDto);
        } else if (categoriaId != null) {
            return trabajadorRepository.findByCategoriaId(categoriaId, pageable)
                    .map(trabajadorMapper::toResponseDto);
        } else if (ciudad != null) {
            return trabajadorRepository.findByCiudad(ciudad, pageable)
                    .map(trabajadorMapper::toResponseDto);
        }
        return trabajadorRepository.findAll(pageable).map(trabajadorMapper::toResponseDto);
    }
}