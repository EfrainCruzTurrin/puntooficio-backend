package com.puntooficio.puntooficio.auth.services.interfaces;

import com.puntooficio.puntooficio.auth.dtos.request.ClienteRegisterRequestDto;
import com.puntooficio.puntooficio.auth.dtos.request.LoginRequestDto;
import com.puntooficio.puntooficio.auth.dtos.request.TrabajadorRegisterRequestDto;
import com.puntooficio.puntooficio.auth.dtos.response.AuthResponseDto;

public interface IAuthService {
    AuthResponseDto login(LoginRequestDto dto);
    AuthResponseDto registerCliente(ClienteRegisterRequestDto dto);
    AuthResponseDto registerTrabajador(TrabajadorRegisterRequestDto dto);
}