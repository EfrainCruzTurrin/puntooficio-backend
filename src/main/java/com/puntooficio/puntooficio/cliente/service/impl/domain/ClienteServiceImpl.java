package com.puntooficio.puntooficio.cliente.service.impl.domain;

import com.puntooficio.puntooficio.cliente.dtos.request.ClienteRequestDto;
import com.puntooficio.puntooficio.cliente.dtos.response.ClienteResponseDto;
import com.puntooficio.puntooficio.cliente.mappers.ClienteMapper;
import com.puntooficio.puntooficio.cliente.models.Cliente;
import com.puntooficio.puntooficio.cliente.repositories.ClienteRepository;
import com.puntooficio.puntooficio.cliente.service.interfaces.domain.IClienteService;
import com.puntooficio.puntooficio.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ClienteResponseDto create(ClienteRequestDto requestDto) {
        Cliente cliente = clienteMapper.toEntity(requestDto);
        cliente.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        return clienteMapper.toDto(clienteRepository.save(cliente));
    }

    @Override
    public ClienteResponseDto findById(Long id) {
        return clienteMapper.toDto(clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", id)));
    }

    @Override
    public ClienteResponseDto update(Long id, ClienteRequestDto requestDto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", id));
        clienteMapper.updateEntityFromDto(requestDto, cliente);
        cliente.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        return clienteMapper.toDto(clienteRepository.save(cliente));
    }

    @Override
    public ClienteResponseDto partialUpdate(Long id, ClienteRequestDto requestDto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", id));
        if (requestDto.getNombreCompleto() != null) cliente.setNombreCompleto(requestDto.getNombreCompleto());
        if (requestDto.getTelefono() != null) cliente.setTelefono(requestDto.getTelefono());
        if (requestDto.getPassword() != null) cliente.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        if (requestDto.getEmail() != null) cliente.setEmail(requestDto.getEmail());
        return clienteMapper.toDto(clienteRepository.save(cliente));
    }

    @Override
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", id));
        clienteRepository.delete(cliente);
    }

    @Override
    public Page<ClienteResponseDto> findAll(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(clienteMapper::toDto);
    }

}