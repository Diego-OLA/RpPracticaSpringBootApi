package com.prueba.Reproductor.mapper;

import com.prueba.Reproductor.dto.GeneroDTO;
import com.prueba.Reproductor.dto.UsuarioDTO;
import com.prueba.Reproductor.model.Genero;
import com.prueba.Reproductor.model.Usuario;

public class GeneroMapper {
    public static GeneroDTO toDTO(Genero entity) {
        if (entity == null) return null;

        GeneroDTO dto = new GeneroDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        return dto;
    }

    public static Genero toEntity(GeneroDTO dto) {
        if (dto == null) return null;

        Genero entity = new Genero();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        return entity;
    }
}
