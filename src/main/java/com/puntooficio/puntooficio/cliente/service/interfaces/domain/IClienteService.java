package com.puntooficio.puntooficio.cliente.service.interfaces.domain;


import com.puntooficio.puntooficio.cliente.dtos.request.ClienteRequestDto;
import com.puntooficio.puntooficio.cliente.dtos.response.ClienteResponseDto;
import com.puntooficio.puntooficio.shared.interfaces.ICrudService;

public interface IClienteService extends ICrudService<ClienteResponseDto, ClienteRequestDto, Long> {
}