package com.prueba.Reproductor.mapper;

import com.prueba.Reproductor.dto.AlbumDTO;
import com.prueba.Reproductor.dto.ArtistaDTO;
import com.prueba.Reproductor.dto.GeneroDTO;
import com.prueba.Reproductor.model.Album;
import com.prueba.Reproductor.model.Artista;

public class AlbumMapper {
    public static AlbumDTO toDTO(Album entity) {
        if (entity == null) return null;

        AlbumDTO dto = new AlbumDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        if(entity.getArtista()!=null){
            ArtistaDTO artistaDTO = new ArtistaDTO();
            artistaDTO.setId(entity.getArtista().getId());
            artistaDTO.setNombre(entity.getArtista().getNombre());
            dto.setArtistaDTO(artistaDTO);
        }

        return dto;
    }
}
