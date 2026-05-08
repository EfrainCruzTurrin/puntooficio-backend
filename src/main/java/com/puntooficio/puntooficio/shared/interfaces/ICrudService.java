package com.puntooficio.puntooficio.shared.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICrudService<ResponseDto, RequestDto, ID> {
    Page<ResponseDto> findAll(Pageable pageable);
    ResponseDto findById(ID id);
    ResponseDto create(RequestDto requestDto);
    ResponseDto update(ID id, RequestDto requestDto);
    ResponseDto partialUpdate(ID id, RequestDto requestDto);
    void delete(ID id);

}