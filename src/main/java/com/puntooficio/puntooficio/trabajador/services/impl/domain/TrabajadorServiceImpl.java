package com.puntooficio.puntooficio.trabajador.services.impl.domain;


import com.puntooficio.puntooficio.categoria.repositories.CategoriaRepository;
import com.puntooficio.puntooficio.ciudad.models.Ciudad;
import com.puntooficio.puntooficio.ciudad.repositories.CiudadRepository;
import com.puntooficio.puntooficio.galeria.dtos.response.GaleriaImagenResponseDto;
import com.puntooficio.puntooficio.galeria.mappers.GaleriaImagenMapper;
import com.puntooficio.puntooficio.galeria.models.GaleriaImagen;
import com.puntooficio.puntooficio.galeria.repositories.GaleriaImagenRepository;
import com.puntooficio.puntooficio.shared.exceptions.BadRequestException;
import com.puntooficio.puntooficio.shared.exceptions.ResourceNotFoundException;
import com.puntooficio.puntooficio.subcategoria.models.Subcategoria;
import com.puntooficio.puntooficio.subcategoria.repositories.SubcategoriaRepository;
import com.puntooficio.puntooficio.trabajador.dtos.request.TrabajadorPanelUpdateRequestDto;
import com.puntooficio.puntooficio.trabajador.dtos.request.TrabajadorRequestDto;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorListaResponseDto;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorPerfilResponseDto;
import com.puntooficio.puntooficio.trabajador.dtos.response.TrabajadorResponseDto;
import com.puntooficio.puntooficio.trabajador.mappers.TrabajadorMapper;
import com.puntooficio.puntooficio.trabajador.models.Trabajador;
import com.puntooficio.puntooficio.trabajador.repositories.TrabajadorRepository;
import com.puntooficio.puntooficio.trabajador.services.interfaces.domain.ITrabajadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TrabajadorServiceImpl implements ITrabajadorService {

    private final TrabajadorRepository trabajadorRepository;
    private final CategoriaRepository categoriaRepository;
    private final SubcategoriaRepository subcategoriaRepository;
    private final TrabajadorMapper trabajadorMapper;
    private final PasswordEncoder passwordEncoder;
    private final CiudadRepository ciudadRepository;
    private final GaleriaImagenRepository galeriaImagenRepository;
    private final GaleriaImagenMapper galeriaImagenMapper;

    @Override
    public TrabajadorResponseDto create(TrabajadorRequestDto dto) {
        Ciudad ciudad = ciudadRepository.findById(dto.getCiudadId())
                .orElseThrow(() -> new ResourceNotFoundException("Ciudad", dto.getCiudadId()));
        Trabajador trabajador = trabajadorMapper.toEntity(dto, ciudad);
        trabajador.setPassword(passwordEncoder.encode(dto.getPassword()));

        return trabajadorMapper.toResponseDto(trabajadorRepository.save(trabajador));
    }

    @Override
    public TrabajadorResponseDto findById(Long id) {
        return trabajadorMapper.toResponseDto(findOrThrow(id));
    }

    @Override
    public TrabajadorResponseDto update(Long id, TrabajadorRequestDto dto) {
        Trabajador existing = findOrThrow(id);
        existing.setNombre(dto.getNombre());
        existing.setApellido(dto.getApellido());
        existing.setTelefono(dto.getTelefono());
        existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        existing.setDni(dto.getDni());
        existing.setFotoPerfil(dto.getFotoPerfil());
        if (dto.getCiudadId() != null) {
            Ciudad ciudad = ciudadRepository.findById(dto.getCiudadId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ciudad", dto.getCiudadId()));
            existing.setCiudad(ciudad);
        }

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
        if (dto.getCiudadId() != null) {
            Ciudad ciudad = ciudadRepository.findById(dto.getCiudadId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ciudad", dto.getCiudadId()));
            existing.setCiudad(ciudad);
        }

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



    @Override
    public Page<TrabajadorResponseDto> findAll(Pageable pageable) {
        return trabajadorRepository.findAll(pageable).map(trabajadorMapper::toResponseDto);
    }



    @Override
    @Transactional(readOnly = true)
    public TrabajadorPerfilResponseDto getPanel(Long trabajadorId) {
        return trabajadorMapper.toPerfilDto(findOrThrow(trabajadorId));
    }

    @Override
    @Transactional
    public TrabajadorPerfilResponseDto updatePanel(Long trabajadorId, TrabajadorPanelUpdateRequestDto dto) {
        Trabajador existing = findOrThrow(trabajadorId);


        if (dto.getCiudadId() != null) {
            Ciudad ciudad = ciudadRepository.findById(dto.getCiudadId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ciudad", dto.getCiudadId()));
            existing.setCiudad(ciudad);
        }
        if (dto.getFotoPerfil() != null) existing.setFotoPerfil(dto.getFotoPerfil());
        if (dto.getBiografia() != null) existing.setBiografia(dto.getBiografia());
        if (dto.getWhatsappContacto() != null) existing.setWhatsappContacto(dto.getWhatsappContacto());
        if (dto.getInstagramUsuario() != null) existing.setInstagramUsuario(dto.getInstagramUsuario());
        if (dto.getMetodoContacto() != null) existing.setMetodoContacto(dto.getMetodoContacto());

        if (dto.getSubcategoriaIds() != null) {
            Set<Subcategoria> nuevas = new HashSet<>(subcategoriaRepository.findAllById(dto.getSubcategoriaIds()));
            if (nuevas.size() != dto.getSubcategoriaIds().size()) {
                throw new ResourceNotFoundException("Una o más subcategorías no existen");
            }
            existing.setSubcategorias(nuevas);
        }

        return trabajadorMapper.toPerfilDto(trabajadorRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public TrabajadorPerfilResponseDto getPerfilPublico(Long trabajadorId) {
        return trabajadorMapper.toPerfilDto(findOrThrow(trabajadorId));
    }
    @Override
    @Transactional(readOnly = true)
    public Page<TrabajadorListaResponseDto> getLista(Long categoriaId, String ciudad, Pageable pageable) {
        Page<Trabajador> page;
        if (categoriaId != null && ciudad != null) {
            page = trabajadorRepository.findByCategoriaIdAndCiudad(categoriaId, ciudad, pageable);
        } else if (categoriaId != null) {
            page = trabajadorRepository.findByCategoriaId(categoriaId, pageable);
        } else if (ciudad != null) {
            page = trabajadorRepository.findByCiudad_Nombre(ciudad, pageable);
        } else {
            page = trabajadorRepository.findAll(pageable);
        }

        List<TrabajadorListaResponseDto> ordenada = page.map(trabajadorMapper::toListaDto)
                .getContent().stream()
                .sorted(Comparator.comparingDouble((TrabajadorListaResponseDto t) ->
                        t.getPuntajePromedio() == null ? 0.0 : t.getPuntajePromedio()).reversed())
                .toList();

        return new PageImpl<>(ordenada, pageable, page.getTotalElements());
    }

    @Override
    public Page<TrabajadorResponseDto> search(Long categoriaId, String ciudad, Pageable pageable) {
        Page<Trabajador> page;
        if (categoriaId != null && ciudad != null) {
            page = trabajadorRepository.findByCategoriaIdAndCiudad(categoriaId, ciudad, pageable);
        } else if (categoriaId != null) {
            page = trabajadorRepository.findByCategoriaId(categoriaId, pageable);
        } else if (ciudad != null) {
            page = trabajadorRepository.findByCiudad_Nombre(ciudad, pageable);
        } else {
            page = trabajadorRepository.findAll(pageable);
        }
        return page.map(trabajadorMapper::toResponseDto);
    }

    @Override
    @Transactional
    public GaleriaImagenResponseDto agregarImagenGaleria(Long trabajadorId, String url) {
        Trabajador trabajador = findOrThrow(trabajadorId);
        if (galeriaImagenRepository.countByTrabajadorId(trabajadorId) >= 6) {
            throw new BadRequestException("Máximo 6 imágenes en la galería");
        }
        GaleriaImagen imagen = new GaleriaImagen(url, trabajador);
        return galeriaImagenMapper.toResponseDto(galeriaImagenRepository.save(imagen));
    }

    @Override
    @Transactional
    public void eliminarImagenGaleria(Long trabajadorId, Long imagenId) {
        GaleriaImagen imagen = galeriaImagenRepository.findById(imagenId)
                .orElseThrow(() -> new ResourceNotFoundException("Imagen", imagenId));
        if (!imagen.getTrabajador().getId().equals(trabajadorId)) {
            throw new BadRequestException("No tenés permiso para eliminar esta imagen");
        }
        galeriaImagenRepository.delete(imagen);
    }
}
