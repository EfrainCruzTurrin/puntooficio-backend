package com.puntooficio.puntooficio.resena.services.impl.domain;

import com.puntooficio.puntooficio.cliente.models.Cliente;
import com.puntooficio.puntooficio.cliente.repositories.ClienteRepository;
import com.puntooficio.puntooficio.resena.dtos.request.ResenaRequestDto;
import com.puntooficio.puntooficio.resena.dtos.response.ResenaResponseDto;
import com.puntooficio.puntooficio.resena.mappers.ResenaMapper;
import com.puntooficio.puntooficio.resena.models.Resena;
import com.puntooficio.puntooficio.resena.repositories.ResenaRepository;
import com.puntooficio.puntooficio.resena.services.interfaces.domain.IResenaService;
import com.puntooficio.puntooficio.shared.exceptions.BadRequestException;
import com.puntooficio.puntooficio.shared.exceptions.ResourceNotFoundException;
import com.puntooficio.puntooficio.trabajador.models.Trabajador;
import com.puntooficio.puntooficio.trabajador.repositories.TrabajadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResenaServiceImpl implements IResenaService {

    private final ResenaRepository resenaRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final ClienteRepository clienteRepository;
    private final ResenaMapper resenaMapper;

    @Override
    public ResenaResponseDto findById(Long id) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resena", id));
        return resenaMapper.toResponseDto(resena);
    }

    @Override
    public ResenaResponseDto create(ResenaRequestDto dto) {
        Trabajador trabajador = trabajadorRepository.findById(dto.getTrabajadorId())
                .orElseThrow(() -> new ResourceNotFoundException("Trabajador", dto.getTrabajadorId()));
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", dto.getClienteId()));
        Resena resena = resenaMapper.toEntity(dto, trabajador, cliente);
        resena.setFechaCreacion(LocalDateTime.now());
        if (resenaRepository.existsByTrabajadorIdAndClienteId(dto.getTrabajadorId(), dto.getClienteId())) {
            throw new BadRequestException("Ya dejaste una reseña para este trabajador.");
        }
        return resenaMapper.toResponseDto(resenaRepository.save(resena));
    }

    @Override
    public ResenaResponseDto update(Long id, ResenaRequestDto dto) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resena", id));
        Trabajador trabajador = trabajadorRepository.findById(dto.getTrabajadorId())
                .orElseThrow(() -> new ResourceNotFoundException("Trabajador", dto.getTrabajadorId()));
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", dto.getClienteId()));
        resenaMapper.updateEntityFromDto(dto, resena, trabajador, cliente);
        return resenaMapper.toResponseDto(resenaRepository.save(resena));
    }

    @Override
    public ResenaResponseDto partialUpdate(Long id, ResenaRequestDto dto) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resena", id));
        Trabajador trabajador = dto.getTrabajadorId() != null
                ? trabajadorRepository.findById(dto.getTrabajadorId())
                  .orElseThrow(() -> new ResourceNotFoundException("Trabajador", dto.getTrabajadorId()))
                : null;
        Cliente cliente = dto.getClienteId() != null
                ? clienteRepository.findById(dto.getClienteId())
                  .orElseThrow(() -> new ResourceNotFoundException("Cliente", dto.getClienteId()))
                : null;
        resenaMapper.partialUpdateEntityFromDto(dto, resena, trabajador, cliente);
        return resenaMapper.toResponseDto(resenaRepository.save(resena));
    }

    @Override
    public void delete(Long id) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resena", id));
        resenaRepository.delete(resena);
    }

    @Override
    public Page<ResenaResponseDto> findAll(Pageable pageable) {
        return resenaRepository.findAll(pageable).map(resenaMapper::toResponseDto);
    }

    @Override
    public Page<ResenaResponseDto> findByTrabajadorId(Long trabajadorId, Pageable pageable) {
        return resenaRepository.findByTrabajadorId(trabajadorId, pageable)
                .map(resenaMapper::toResponseDto);
    }
}