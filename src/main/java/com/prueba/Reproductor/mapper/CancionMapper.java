package com.prueba.Reproductor.mapper;

import com.prueba.Reproductor.dto.AlbumDTO;
import com.prueba.Reproductor.dto.ArtistaDTO;
import com.prueba.Reproductor.dto.CancionDTO;
import com.prueba.Reproductor.model.Cancion;

public class CancionMapper {
    public static CancionDTO toDTO(Cancion entity){
        if (entity == null) return null;
        CancionDTO dto = new CancionDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setNumeroTrack(entity.getNumeroTrack());
        dto.setDuracionMinutos(entity.getDuracionMinutos());

        if(entity.getAlbum()!=null){
          /*  AlbumDTO albumDTO = new AlbumDTO();
            albumDTO.setId(entity.getAlbum().getId());
            albumDTO.setTitulo(entity.getAlbum().getTitulo());
        */
            AlbumDTO albumDTO = AlbumMapper.toDTO(entity.getAlbum());
            dto.setAlbumDTO(albumDTO);

        }
        if(entity.getArtista()!=null){
            ArtistaDTO artistaDTO = ArtistaMapper.toDTO(entity.getArtista());
            dto.setArtistaDTO(artistaDTO);

        }

        return dto;
    }
}
