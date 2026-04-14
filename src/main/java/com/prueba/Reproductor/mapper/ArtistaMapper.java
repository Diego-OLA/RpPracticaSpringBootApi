package com.prueba.Reproductor.mapper;

import com.prueba.Reproductor.dto.ArtistaDTO;
import com.prueba.Reproductor.dto.GeneroDTO;
import com.prueba.Reproductor.model.Artista;
import com.prueba.Reproductor.model.Genero;

public class ArtistaMapper {
    public static ArtistaDTO toDTO(Artista entity) {
        if (entity == null) return null;

        ArtistaDTO dto = new ArtistaDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        if(entity.getGenero()!=null){
            GeneroDTO genDTO = new GeneroDTO();
            genDTO.setId(entity.getGenero().getId());
            genDTO.setNombre(entity.getGenero().getNombre());
            dto.setGeneroDTO(genDTO);
        }

        return dto;
    }
}
