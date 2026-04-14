package com.prueba.Reproductor.mapper;

import com.prueba.Reproductor.dto.GeneroDTO;
import com.prueba.Reproductor.dto.UsuarioDTO;
import com.prueba.Reproductor.model.Genero;
import com.prueba.Reproductor.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {
    public static UsuarioDTO toDTO(Usuario entity) {
        if (entity == null) return null;

        UsuarioDTO dto = new UsuarioDTO();

        dto.setId(entity.getId());
        dto.setNombre(entity.getNombres());
        dto.setApellidos(entity.getApellidos());
        dto.setCorreo(entity.getCorreo());
        dto.setFechaNacimiento(entity.getFechaNacimiento());
        dto.setTipoCuenta(entity.getTipoCuenta());
        dto.setTarjetaCredito(entity.getTarjetaCredito());

        if (entity.getGeneros() != null) {
            dto.setGeneros(
                    entity.getGeneros()
                            .stream()
                            .map(ug -> GeneroMapper.toDTO(ug.getGenero()))
                            .toList()
            );
        }

        return dto;
    }

}
